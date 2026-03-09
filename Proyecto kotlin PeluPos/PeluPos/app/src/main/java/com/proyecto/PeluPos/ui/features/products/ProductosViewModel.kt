package com.proyecto.PeluPos.ui.features.products

import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.models.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductosViewModel @Inject constructor(
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductosUiState())
    val uiState: StateFlow<ProductosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        val productos = productoRepository.getProductos()
        _uiState.update {
            it.copy(
                todosLosProductos = productos,
                productosVisibles = filtrarProductos(productos, it.searchQuery)
            )
        }
    }

    private fun filtrarProductos(lista: List<Producto>, query: String): List<Producto> {
        if (query.isBlank()) return lista
        return lista.filter { it.nombre.contains(query, ignoreCase = true) }
    }

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
        }
    }

    private fun guardarProducto() {
        val state = _uiState.value
        val nuevoProducto = Producto(
            idProducto = state.editandoProductoId ?: System.currentTimeMillis(),
            nombre = state.formNombre,
            precioCompra = state.formPrecioCompra.replace(",", ".").toDoubleOrNull() ?: 0.0,
            precioVenta = state.formPrecioVenta.replace(",", ".").toDoubleOrNull() ?: 0.0,
            stock = state.formStock.toIntOrNull() ?: 0
        )

        if (state.editandoProductoId != null) {
            productoRepository.updateProducto(nuevoProducto)
        } else {
            productoRepository.insert(nuevoProducto)
        }
        cargarDatos()
    }

    private fun borrarProducto() {
        _uiState.value.editandoProductoId?.let { id ->
            productoRepository.delete(id)
            cargarDatos()
        }
    }
}