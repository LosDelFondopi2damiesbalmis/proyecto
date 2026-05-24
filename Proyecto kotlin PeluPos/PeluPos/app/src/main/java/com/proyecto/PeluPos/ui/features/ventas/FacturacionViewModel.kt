package com.proyecto.PeluPos.ui.features.ventas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.data.mocks.cliente.ClienteRepository
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
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
    private val facturaRepository: FacturaRepository,
    private val empleadoRepository: EmpleadoRepository, // 🚀 Añadido el repositorio real
    private val clienteRepository: ClienteRepository    // 🚀 Añadido el repositorio real
) : ViewModel() {

    private val _uiState = MutableStateFlow(FacturacionUiState())
    val uiState: StateFlow<FacturacionUiState> = _uiState.asStateFlow()

    init {
        cargarDatosIniciales()
        observarCarrito()
    }

    private fun cargarDatosIniciales() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // 1. Cargamos TODOS los catálogos asíncronamente desde Tomcat
                val productos = productoRepository.obtenerProductos()
                val servicios = servicioRepository.getServicios()
                val empleadosReales = empleadoRepository.getEmpleados() // 🚀 Datos reales de red
                val clientesReales = clienteRepository.getClientes()    // 🚀 Datos reales de red

                // 2. Traemos el historial real de facturas emitidas
                val historialFacturas = facturaRepository.getFacturas()

                // 3. Actualizamos el estado con la información real de la BD
                _uiState.update {
                    it.copy(
                        productosDisponibles = productos,
                        serviciosDisponibles = servicios,
                        empleadosDisponibles = empleadosReales,
                        clientesDisponibles = clientesReales,
                        todasLasFacturas = historialFacturas,
                        facturasVisibles = filtradasPorQuery(historialFacturas, it.searchQuery),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al sincronizar datos con el servidor: ${e.message}"
                    )
                }
            }
        }
    }

    private fun observarCarrito() {
        viewModelScope.launch {
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

            FacturacionEvent.OnGuardarFactura -> guardarFactura()

            // --- HISTORIAL ---
            is FacturacionEvent.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        facturasVisibles = filtradasPorQuery(it.todasLasFacturas, event.query)
                    )
                }
            }
        }
    }

    private fun guardarFactura() {
        val currentState = _uiState.value
        val empleado = currentState.empleadoSeleccionado ?: return
        val cliente = currentState.clienteSeleccionado ?: return

        // Mapeamos los precios de venta asegurando que manejen correctamente tipos de datos opcionales
        val montoTotal = currentState.carritoProductos.sumOf { it.precioVenta ?: 0.0 } +
                currentState.carritoServicios.sumOf { it.precio ?: 0.0 }

        val nuevaFactura = Factura(
            idFactura = 0L, // 🚀 Forzamos 0L para que MySQL autoincremente el número de factura
            monto = montoTotal,
            fecha = Date(),
            pendiente = false,
            tipoPago = currentState.tipoPago,
            cliente = cliente,
            empleado = empleado,
            productos = currentState.carritoProductos.toMutableList(),
            servicios = currentState.carritoServicios.toMutableList()
        )

        // 🚀 Metemos la inserción en una corrutina para realizar la petición POST por red
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                // Enviamos la factura a la API REST de Tomcat
                facturaRepository.createFactura(nuevaFactura)

                // Si el servidor responde exitosamente, vaciamos el carrito físico de la app
                carritoRepository.vaciarCarrito()

                _uiState.update {
                    it.copy(
                        mensaje = "¡Venta y Factura procesadas con éxito!",
                        empleadoSeleccionado = null,
                        clienteSeleccionado = null
                    )
                }

                // Recargamos los catálogos y el historial para mostrar la nueva factura en la lista
                cargarDatosIniciales()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "No se pudo registrar la factura: ${e.message}"
                    )
                }
            }
        }
    }

    // Función auxiliar limpia para aplicar filtros de manera reactiva
    private fun filtradasPorQuery(lista: List<Factura>, query: String): List<Factura> {
        if (query.isBlank()) return lista
        return lista.filter { factura ->
            (factura.empleado?.nombre?.contains(query, ignoreCase = true) ?: false) ||
                    (factura.cliente?.nombre?.contains(query, ignoreCase = true) ?: false)
        }
    }
}