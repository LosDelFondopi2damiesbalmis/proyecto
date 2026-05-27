package com.proyecto.PeluPos.ui.features.ventas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.data.mocks.cliente.ClienteRepository
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.factura.FacturaRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.FacturaProductoDto
import com.proyecto.PeluPos.models.FacturaProductoPKDto
import com.proyecto.PeluPos.models.FacturaServicioDto
import com.proyecto.PeluPos.models.FacturaServicioPKDto
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

    fun cargarDatosIniciales() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val facturas = facturaRepository.getFacturas()
                _uiState.update { it.copy(
                    todasLasFacturas = facturas,
                    facturasVisibles = facturas // 👈 ¡ESTA ES LA LÍNEA MÁGICA!
                ) }
            } catch (e: Exception) {
                android.util.Log.e("CARGA_DATOS", "Fallo en Facturas: ${e.message}")
                _uiState.update { it.copy(
                    todasLasFacturas = emptyList(),
                    facturasVisibles = emptyList() // Vaciamos ambas por seguridad
                ) }
            }

            // 🚀 2. Intentamos cargar Clientes
            try {
                val clientes = clienteRepository.getClientes()
                _uiState.update { it.copy(clientesDisponibles = clientes) }
            } catch (e: Exception) {
                android.util.Log.e("CARGA_DATOS", "Fallo en Clientes: ${e.message}")
            }

            // 🚀 3. Intentamos cargar Empleados
            try {
                val empleados = empleadoRepository.getEmpleados()
                _uiState.update { it.copy(empleadosDisponibles = empleados) }
            } catch (e: Exception) {
                android.util.Log.e("CARGA_DATOS", "Fallo en Empleados: ${e.message}")
            }

            // 🚀 4. Intentamos cargar Productos
            try {
                val productos = productoRepository.obtenerProductos()
                _uiState.update { it.copy(productosDisponibles = productos) }
            } catch (e: Exception) {
                android.util.Log.e("CARGA_DATOS", "Fallo en Productos: ${e.message}")
            }

            // 🚀 5. Intentamos cargar Servicios (¡Aquí está el nuevo!)
            try {
                // Asegúrate de que el nombre de la función coincida con la de tu repositorio real
                val servicios = servicioRepository.getServicios()
                _uiState.update { it.copy(serviciosDisponibles = servicios) }
            } catch (e: Exception) {
                android.util.Log.e("CARGA_DATOS", "Fallo en Servicios: ${e.message}")
            }

            _uiState.update { it.copy(isLoading = false) }
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
            is FacturacionEvent.OnEliminarFactura -> {
                devolverFactura(event.idFactura)
            }
            FacturacionEvent.OnGuardarFactura -> guardarFactura()
            FacturacionEvent.OnRecargarDatos -> cargarDatosIniciales()

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

        val montoTotal = currentState.carritoProductos.sumOf { it.precioVenta ?: 0.0 } +
                currentState.carritoServicios.sumOf { it.precio ?: 0.0 }

        val nuevaFactura = Factura(
            idFactura = 0L,
            monto = montoTotal,
            fecha = Date(),
            pendiente = false,
            tipoPago = currentState.tipoPago,
            cliente = cliente,
            empleado = empleado,
            productos = mutableListOf(),
            servicios = mutableListOf()
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                // 🚀 AGRUPAMOS LOS PRODUCTOS (Para saber la cantidad total que se compra de cada uno)
                val productosAgrupados = currentState.carritoProductos
                    .groupBy { it.idProducto }
                    .map { (_, lista) -> Pair(lista.first(), lista.size) }

                // 🛡️ BARRERA DE CONTROL: Comprobar si hay stock suficiente de TODO antes de empezar
                val productoSinStock = productosAgrupados.find { (producto, cantidadRequerida) ->
                    (producto.stock ?: 0) < cantidadRequerida
                }

                if (productoSinStock != null) {
                    // Si falta stock de algo, paramos y saltamos directamente al catch sin tocar la BD
                    val nombre = productoSinStock.first.nombre ?: "Producto"
                    val cant = productoSinStock.second
                    val stockActual = productoSinStock.first.stock ?: 0
                    throw Exception("Stock insuficiente de '$nombre'. Pides $cant unidades pero solo quedan $stockActual.")
                }

                // 🔒 ESCUDO ACTIVADO: Si el código llega aquí, hay stock de todo y prohibimos cancelar a medias
                kotlinx.coroutines.withContext(kotlinx.coroutines.NonCancellable) {

                    // PASO 1: Guardamos la cabecera en Tomcat
                    val facturaGuardada = facturaRepository.createFactura(nuevaFactura)
                    val idGenerado = facturaGuardada.idFactura

                    // PASO 2: Guardamos cada PRODUCTO y RESTAMOS el stock en el servidor
                    productosAgrupados.forEach { (producto, cantidadTotal) ->
                        // A) Vinculamos el producto a la factura intermedia
                        val fpDto = FacturaProductoDto(
                            facturaProductoPK = FacturaProductoPKDto(idGenerado, producto.idProducto),
                            cantidad = cantidadTotal,
                            precioVendido = producto.precioVenta
                        )
                        facturaRepository.addProductoAFactura(fpDto)

                        // B) 📉 RESTA DE STOCK: Calculamos el nuevo stock y lo mandamos con tu PUT
                        val stockActual = producto.stock ?: 0
                        val nuevoStock = stockActual - cantidadTotal

                        // Creamos una copia del producto modificando solo el stock
                        val productoModificado = producto.copy(stock = nuevoStock)

                        // Llamamos a tu repositorio para actualizarlo en Tomcat
                        productoRepository.actualizarProducto(productoModificado)
                    }

                    // PASO 3: Guardamos cada SERVICIO (únicos)
                    val serviciosUnicos = currentState.carritoServicios.distinctBy { it.idServicio }
                    serviciosUnicos.forEach { servicio ->
                        val fsDto = FacturaServicioDto(
                            facturaServicioPK = FacturaServicioPKDto(idGenerado, servicio.idServicio),
                            cantidad = 1,
                            precioCobrado = servicio.precio
                        )
                        facturaRepository.addServicioAFactura(fsDto)
                    }

                    // PASO 4: Vaciamos el carrito visual y recargamos todo
                    carritoRepository.vaciarCarrito()
                } // 🔓 Fin del escudo

                _uiState.update {
                    it.copy(
                        mensaje = "¡Venta procesada, stock actualizado y factura guardada con éxito!",
                        empleadoSeleccionado = null,
                        clienteSeleccionado = null
                    )
                }

                cargarDatosIniciales()

            } catch (e: Exception) {
                e.printStackTrace()
                android.util.Log.e("PRUEBA_FACTURA", "CRASH EN GUARDAR: ${e.message}", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "No se pudo registrar la factura completa."
                    )
                }
            }
        }
    }
    fun devolverFactura(idFactura: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // 1. Buscamos la factura completa con todos sus detalles
                val factura = facturaRepository.getFactura(idFactura)
                    ?: throw Exception("No se encontró la factura para devolver")

                // 2. Usamos el ESCUDO para que no se cancele el proceso a medias
                kotlinx.coroutines.withContext(kotlinx.coroutines.NonCancellable) {

                    // 3. PASO A: Sumar stock de los productos devueltos
                    factura.productos.forEach { producto ->
                        val nuevoStock = (producto.stock ?: 0) + 1 // Sumamos 1 por cada producto en la lista
                        val productoModificado = producto.copy(stock = nuevoStock)
                        productoRepository.actualizarProducto(productoModificado)
                    }

                    // 4. PASO B: Borrar relaciones (Productos)
                    factura.productos.forEach { producto ->
                        facturaRepository.deleteFacturaProducto(idFactura, producto.idProducto)
                    }

                    // 5. PASO C: Borrar relaciones (Servicios)
                    factura.servicios.forEach { servicio ->
                        facturaRepository.deleteFacturaServicio(idFactura, servicio.idServicio)
                    }

                    // 6. PASO D: Borrar la cabecera
                    facturaRepository.deleteFactura(idFactura)
                }

                // 7. Éxito!
                _uiState.update { it.copy(isLoading = false, mensaje = "Devolución realizada y stock actualizado") }
                cargarDatosIniciales() // Recargar historial

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false, error = "Error al devolver: ${e.message}") }
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