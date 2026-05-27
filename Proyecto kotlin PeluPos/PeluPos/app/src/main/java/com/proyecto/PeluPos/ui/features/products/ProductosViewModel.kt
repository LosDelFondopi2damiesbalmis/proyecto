package com.proyecto.PeluPos.ui.features.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.models.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.proyecto.PeluPos.navigation.ProductoFormRoute
import kotlinx.coroutines.launch


@HiltViewModel
class ProductosViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductosUiState())
    val uiState: StateFlow<ProductosUiState> = _uiState.asStateFlow()
    private val idProductoAEditar = savedStateHandle.toRoute<ProductoFormRoute>().idProducto
    init {
        cargarDatos()
    }

    // --------------------------------------------------------
    // 1. CARGAR DATOS (GET) - Ahora con internet
    // --------------------------------------------------------
    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                val productosApi = productoRepository.obtenerProductos()

                // 🚀 3. Estado base con la lista
                var newState = _uiState.value.copy(
                    todosLosProductos = productosApi,
                    productosVisibles = filtrarProductos(productosApi, _uiState.value.searchQuery),
                    isLoading = false
                )

                // 🚀 4. LA MAGIA DE LA EDICIÓN:
                // Si la ruta tiene ID, buscamos el producto en la lista recién cargada
                if (idProductoAEditar != null) {
                    val prod = productosApi.find { it.idProducto == idProductoAEditar }
                    if (prod != null) {
                        newState = newState.copy(
                            editandoProductoId = prod.idProducto,
                            formNombre = prod.nombre,
                            formPrecioCompra = prod.precioCompra.toString(),
                            formPrecioVenta = prod.precioVenta.toString(),
                            formStock = prod.stock.toString()
                        )
                    }
                } else {
                    // Si no hay ID, nos aseguramos de que el formulario esté vacío
                    newState = newState.copy(
                        editandoProductoId = null,
                        formNombre = "",
                        formPrecioCompra = "",
                        formPrecioVenta = "",
                        formStock = ""
                    )
                }

                _uiState.value = newState
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun filtrarProductos(lista: List<Producto>, query: String): List<Producto> {
        if (query.isBlank()) return lista
        return lista.filter { it.nombre.contains(query, ignoreCase = true) }
    }

    // --------------------------------------------------------
    // 2. GESTIÓN DE EVENTOS (Se queda igual, ¡estaba perfecto!)
    // --------------------------------------------------------
    fun onEvent(event: ProductosEvent) {
        when (event) {
            ProductosEvent.CargarDatos -> cargarDatos()
            is ProductosEvent.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        productosVisibles = filtrarProductos(it.todosLosProductos, event.query)
                    )
                }
            }

            ProductosEvent.PrepararNuevoProducto -> {
                _uiState.update {
                    it.copy(editandoProductoId = null, formNombre = "", formPrecioCompra = "", formPrecioVenta = "", formStock = "")
                }
            }

            is ProductosEvent.PrepararEdicion -> {
                val producto = _uiState.value.todosLosProductos.find { it.idProducto == event.idProducto }
                producto?.let { prod ->
                    _uiState.update {
                        it.copy(
                            editandoProductoId = prod.idProducto,
                            formNombre = prod.nombre,
                            formPrecioCompra = prod.precioCompra.toString(),
                            formPrecioVenta = prod.precioVenta.toString(),
                            formStock = prod.stock.toString()
                        )
                    }
                }
            }

            is ProductosEvent.OnNombreChange -> _uiState.update { it.copy(formNombre = event.nombre) }
            is ProductosEvent.OnPrecioCompraChange -> _uiState.update { it.copy(formPrecioCompra = event.precio) }
            is ProductosEvent.OnPrecioVentaChange -> _uiState.update { it.copy(formPrecioVenta = event.precio) }
            is ProductosEvent.OnStockChange -> {
                if (event.stock.isEmpty() || event.stock.all { it.isDigit() }) {
                    _uiState.update { it.copy(formStock = event.stock) }
                }
            }

            ProductosEvent.GuardarProducto -> guardarProducto()
            ProductosEvent.BorrarProducto -> borrarProducto()

            // Añadimos este evento opcional por si quieres limpiar el Toast de éxito
        }
    }

    // --------------------------------------------------------
    // 3. GUARDAR PRODUCTO (POST o PUT) - Ahora con internet
    // --------------------------------------------------------
    // --------------------------------------------------------
    // 3. GUARDAR PRODUCTO (POST o PUT)
    // --------------------------------------------------------
    private fun guardarProducto() {
        val state = _uiState.value
        val nuevoProducto = Producto(
            // SOLUCIÓN 1: Si es nuevo (null), le ponemos 0L para que no falle el tipo Long
            idProducto = state.editandoProductoId ?: 0L,
            nombre = state.formNombre,
            precioCompra = state.formPrecioCompra.replace(",", ".").toDoubleOrNull() ?: 0.0,
            precioVenta = state.formPrecioVenta.replace(",", ".").toDoubleOrNull() ?: 0.0,
            stock = state.formStock.toIntOrNull() ?: 0
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoProductoId != null) {
                    productoRepository.actualizarProducto(nuevoProducto)
                } else {
                    productoRepository.crearProducto(nuevoProducto)
                }

                // SOLUCIÓN 2: Ponemos el mensaje a mano
                _uiState.update {
                    it.copy(
                        mensaje = "Producto guardado correctamente",
                        editandoProductoId = null,
                        formNombre = "",
                        formPrecioCompra = "",
                        formPrecioVenta = "",
                        formStock = "",
                        isLoading = false
                    )
                }
                cargarDatos()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // --------------------------------------------------------
    // 4. BORRAR PRODUCTO (DELETE)
    // --------------------------------------------------------
    private fun borrarProducto() {
        _uiState.value.editandoProductoId?.let { id ->
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                try {
                    // 1. Intentamos borrarlo de verdad en la base de datos
                    productoRepository.borrarProducto(id.toInt())

                    // 2. Si sale bien, mostramos el mensaje normal
                    _uiState.update {
                        it.copy(mensaje = "Producto eliminado", isLoading = false)
                    }
                    cargarDatos()

                } catch (e: retrofit2.HttpException) {

                    if (e.code() == 400) {
                        try {
                            // Buscamos el producto en la lista que tienes cargada
                            val productoActual = _uiState.value.todosLosProductos.find { it.idProducto?.toInt() == id.toInt() }

                            if (productoActual != null) {
                                // Le cambiamos el nombre añadiendo una marca secreta
                                val productoOculto = productoActual.copy(
                                    nombre = "[OCULTO] " + productoActual.nombre,
                                    precioVenta = 0.0,
                                    stock = 0
                                )

                                // Llamamos a tu función de editar (usa el nombre exacto que tengas en tu repositorio)
                                productoRepository.actualizarProducto( productoOculto)

                                _uiState.update {
                                    it.copy(mensaje = "El producto tenía ventas, así que se ha ocultado.", isLoading = false)
                                }
                                cargarDatos()
                            }
                        } catch (editError: Exception) {
                            _uiState.update { it.copy(error = "No se pudo ocultar el producto", isLoading = false) }
                        }
                    } else {
                        // Si es otro error (ej: 500)
                        _uiState.update { it.copy(error = "Error del servidor: ${e.code()}", isLoading = false) }
                    }

                } catch (e: Exception) {
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
            }
        }
    }
}