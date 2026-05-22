package com.proyecto.PeluPos.ui.features.products

import com.proyecto.PeluPos.models.Producto

data class ProductosUiState(
    // Tus listas y buscador
    val todosLosProductos: List<Producto> = emptyList(),
    val productosVisibles: List<Producto> = emptyList(),
    val searchQuery: String = "",

    // Tu Formulario
    val editandoProductoId: Long? = null,
    val formNombre: String = "",
    val formPrecioCompra: String = "",
    val formPrecioVenta: String = "",
    val formStock: String = "",

    // NUEVO: Control de la API
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = formNombre.isNotBlank() &&
                formPrecioCompra.toDoubleOrNull() != null &&
                formPrecioVenta.toDoubleOrNull() != null &&
                formStock.toIntOrNull() != null

    val totalProductos: Int get() = productosVisibles.size
    val stockBajoCount: Int get() = productosVisibles.count { it.stock < 5 }
    val valorTotal: Double get() = productosVisibles.sumOf { it.precioVenta * it.stock }
}