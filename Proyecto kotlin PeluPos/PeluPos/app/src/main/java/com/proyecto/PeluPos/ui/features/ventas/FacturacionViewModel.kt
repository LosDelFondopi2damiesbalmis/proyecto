package com.proyecto.PeluPos.ui.features.ventas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mappers.toEntity
import com.proyecto.PeluPos.data.mappers.toModel
import com.proyecto.PeluPos.data.room.dao.*
import com.proyecto.PeluPos.data.room.entity.*
import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.models.Factura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FacturacionViewModel @Inject constructor(
    private val productoDao: ProductoDao,
    private val servicioDao: ServicioDao,
    private val clienteDao: ClienteDao,
    private val empleadoDao: EmpleadoDao,
    private val facturaDao: FacturaDao,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FacturacionUiState())
    val uiState: StateFlow<FacturacionUiState> = _uiState.asStateFlow()

    init {
        observarDatos()
        observarCarrito()
    }

    // ---------------------------------------------------------
    // ROOM REACTIVO TOTAL
    // ---------------------------------------------------------
    private fun observarDatos() {
        viewModelScope.launch {
            combine(
                productoDao.getAllFlow(),
                servicioDao.getAllFlow(),
                clienteDao.getAllClientesFlow(),
                empleadoDao.getAllFlow(),
                facturaDao.getAllFlow()
            ) { productosE: List<ProductoEntity>,
                serviciosE: List<ServicioEntity>,
                clientesE: List<ClienteEntity>,
                empleadosE: List<EmpleadoEntity>,
                facturasE: List<FacturaEntity> ->

                // Mapear productos
                val productos = productosE.map { it.toModel() }

                // Mapear empleados primero para los servicios y facturas
                val empleados = empleadosE.map { it.toModel() }

                // Mapear servicios, buscando su empleado correspondiente
                val servicios = serviciosE.mapNotNull { entity ->
                    val empleado = empleados.find { it.idEmpleado == entity.empleadoId }
                    empleado?.let { entity.toModel(it) } // solo si existe
                }

                // Mapear clientes
                val clientes = clientesE.map { it.toModel() }

                // Mapear facturas, buscando cliente y empleado correspondientes
                val facturas = facturasE.mapNotNull { entity ->
                    val cliente = clientes.find { it.idCliente == entity.clienteId }
                    val empleado = empleados.find { it.idEmpleado == entity.empleadoId }
                    if (cliente != null && empleado != null) {
                        entity.toModel(cliente, empleado)
                    } else null
                }

                // Creamos el estado parcial
                FacturacionUiState(
                    productosDisponibles = productos,
                    serviciosDisponibles = servicios,
                    empleadosDisponibles = empleados,
                    clientesDisponibles = clientes,
                    todasLasFacturas = facturas,
                    facturasVisibles = facturas
                )
            }.collect { nuevoState ->
                _uiState.update {
                    it.copy(
                        productosDisponibles = nuevoState.productosDisponibles,
                        serviciosDisponibles = nuevoState.serviciosDisponibles,
                        empleadosDisponibles = nuevoState.empleadosDisponibles,
                        clientesDisponibles = nuevoState.clientesDisponibles,
                        todasLasFacturas = nuevoState.todasLasFacturas,
                        facturasVisibles = nuevoState.facturasVisibles
                    )
                }
            }
        }
    }

    // ---------------------------------------------------------
    // CARRITO (Flow en memoria)
    // ---------------------------------------------------------
    private fun observarCarrito() {
        viewModelScope.launch {
            carritoRepository.productosEscogidos.collect { lista ->
                _uiState.update { it.copy(carritoProductos = lista) }
            }
        }

        viewModelScope.launch {
            carritoRepository.serviciosEscogidos.collect { lista ->
                _uiState.update { it.copy(carritoServicios = lista) }
            }
        }
    }

    // ---------------------------------------------------------
    // EVENTOS
    // ---------------------------------------------------------
    fun onEvent(event: FacturacionEvent) {
        when (event) {
            is FacturacionEvent.OnEmpleadoSeleccionado ->
                _uiState.update { it.copy(empleadoSeleccionado = event.empleado) }

            is FacturacionEvent.OnClienteSeleccionado ->
                _uiState.update { it.copy(clienteSeleccionado = event.cliente) }

            is FacturacionEvent.OnTipoPagoSeleccionado ->
                _uiState.update { it.copy(tipoPago = event.tipoPago) }

            FacturacionEvent.OnGuardarFactura -> {
                guardarFactura()
                carritoRepository.vaciarCarrito()
            }

            is FacturacionEvent.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                filtrarFacturas(event.query)
            }
        }
    }

    // ---------------------------------------------------------
    // GUARDAR FACTURA
    // ---------------------------------------------------------
    private fun guardarFactura() {
        val state = _uiState.value
        val empleado = state.empleadoSeleccionado ?: return
        val cliente = state.clienteSeleccionado ?: return

        val montoTotal = state.carritoProductos.sumOf { it.precioVenta } +
                state.carritoServicios.sumOf { it.precio }

        val nuevaFactura = Factura(
            idFactura = System.currentTimeMillis(),
            monto = montoTotal,
            fecha = Date(),
            pendiente = false,
            tipoPago = state.tipoPago,
            cliente = cliente,
            empleado = empleado,
            productos = state.carritoProductos.toMutableList(),
            servicios = state.carritoServicios.toMutableList()
        )

        viewModelScope.launch {
            facturaDao.insert(nuevaFactura.toEntity())
            // Flow lo actualizará automáticamente
        }
    }

    // ---------------------------------------------------------
    // FILTRO HISTORIAL
    // ---------------------------------------------------------
    private fun filtrarFacturas(query: String) {
        val state = _uiState.value
        if (query.isBlank()) {
            _uiState.update { it.copy(facturasVisibles = state.todasLasFacturas) }
        } else {
            val filtradas = state.todasLasFacturas.filter {
                it.empleado.nombre.contains(query, true) ||
                        it.cliente.nombre.contains(query, true)
            }
            _uiState.update { it.copy(facturasVisibles = filtradas) }
        }
    }
}