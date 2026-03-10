package com.proyecto.PeluPos.ui.features.ventas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.data.mocks.factura.FacturaRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FacturacionViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val servicioRepository: ServicioRepository,
    private val carritoRepository: CarritoRepository,
    private val facturaRepository: FacturaRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(FacturacionUiState())
    val uiState: StateFlow<FacturacionUiState> = _uiState.asStateFlow()

    init {
        cargarDatosIniciales()
        observarCarrito()
    }

    private fun cargarDatosIniciales() {
        viewModelScope.launch {
            // 1. Cargamos de tus repositorios inyectados
            val productos = productoRepository.getProductos()
            val servicios = servicioRepository.getServicios()

            // 2. Simulamos la carga de empleados y clientes (aquí usarías sus repositorios)
            val empleadosFalsos = listOf(
                Empleado(1L, telefono = 6565454,"carlos@pe.com", "Peluquero", nombre = "Carlos"),
                Empleado(2L, 643843, "elena@pe.com", "Estilista", nombre = "Elena" )
            )
            val clientesFalsos = listOf(
                Cliente(1L, "Juan Pérez", 5.5, 655000111),
                Cliente(2L, "María López", 6.3, 655000222)
            )

            // 3. Actualizamos el estado con los catálogos listos
            _uiState.update {
                it.copy(
                    productosDisponibles = productos, // Si lo añades a tu UiState
                    serviciosDisponibles = servicios, // Si lo añades a tu UiState
                    empleadosDisponibles = empleadosFalsos,
                    clientesDisponibles = clientesFalsos
                )
            }
        }
    }
    private fun observarCarrito() {
        viewModelScope.launch {
            // Cada vez que el CarritoRepository cambie, actualizamos el UiState
            carritoRepository.productosEscogidos.collect { listaProductos ->
                _uiState.update { it.copy(carritoProductos = listaProductos) }
            }
        }
        viewModelScope.launch {
            carritoRepository.serviciosEscogidos.collect { listaServicios ->
                _uiState.update { it.copy(carritoServicios = listaServicios) }
            }
        }
    }

    fun onEvent(event: FacturacionEvent) {
        when (event) {

            // --- CREAR FACTURA ---
            is FacturacionEvent.OnEmpleadoSeleccionado -> _uiState.update { it.copy(empleadoSeleccionado = event.empleado) }
            is FacturacionEvent.OnClienteSeleccionado -> _uiState.update { it.copy(clienteSeleccionado = event.cliente) }
            is FacturacionEvent.OnTipoPagoSeleccionado -> _uiState.update { it.copy(tipoPago = event.tipoPago) }
            FacturacionEvent.OnGuardarFactura -> {
                guardarFactura()
                carritoRepository.vaciarCarrito()
            }

            // --- HISTORIAL ---
            is FacturacionEvent.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                filtrarFacturas(event.query)
            }
        }
    }

    private fun guardarFactura() {
        val currentState = _uiState.value
        val empleado = currentState.empleadoSeleccionado ?: return
        val cliente = currentState.clienteSeleccionado ?: return

        val montoTotal = currentState.carritoProductos.sumOf { it.precioVenta } +
                currentState.carritoServicios.sumOf { it.precio }

        val nuevaFactura = Factura(
            idFactura = System.currentTimeMillis(),
            monto = montoTotal,
            fecha = Date(),
            pendiente = false,
            tipoPago = currentState.tipoPago,
            cliente = cliente,
            empleado = empleado,
            productos = currentState.carritoProductos.toMutableList(),
            servicios = currentState.carritoServicios.toMutableList()
        )

        facturaRepository.insert(nuevaFactura)

        _uiState.update {
            val nuevasFacturas = it.todasLasFacturas + nuevaFactura
            it.copy(
                todasLasFacturas = nuevasFacturas,
                facturasVisibles = nuevasFacturas,
                carritoProductos = emptyList(),
                carritoServicios = emptyList(),
                empleadoSeleccionado = null,
                clienteSeleccionado = null
            )
        }
    }

    private fun filtrarFacturas(query: String) {
        val currentState = _uiState.value
        if (query.isBlank()) {
            _uiState.update { it.copy(facturasVisibles = it.todasLasFacturas) }
        } else {
            val filtradas = currentState.todasLasFacturas.filter { factura ->
                factura.empleado.nombre.contains(query, ignoreCase = true) ||
                        factura.cliente.nombre.contains(query, ignoreCase = true)
            }
            _uiState.update { it.copy(facturasVisibles = filtradas) }
        }
    }
}