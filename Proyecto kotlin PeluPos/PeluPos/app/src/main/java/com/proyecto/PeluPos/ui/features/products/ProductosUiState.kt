package com.proyecto.PeluPos.ui.features.products

import com.proyecto.PeluPos.models.Producto

data class ProductosUiState(
    val todosLosProductos: List<Producto> = emptyList(),
    val productosVisibles: List<Producto> = emptyList(),
    val searchQuery: String = "",

    // Formulario
    val editandoProductoId: Long? = null,
    val formNombre: String = "",
    val formPrecioCompra: String = "",
    val formPrecioVenta: String = "",
    val formStock: String = ""
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