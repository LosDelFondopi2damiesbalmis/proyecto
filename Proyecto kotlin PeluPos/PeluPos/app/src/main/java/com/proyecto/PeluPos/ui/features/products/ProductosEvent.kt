package com.proyecto.PeluPos.ui.features.products

sealed interface ProductosEvent {
    object CargarDatos : ProductosEvent
    data class OnSearchQueryChange(val query: String) : ProductosEvent

    // Formulario
    object PrepararNuevoProducto : ProductosEvent
    data class PrepararEdicion(val idProducto: Long) : ProductosEvent
    data class OnNombreChange(val nombre: String) : ProductosEvent
    data class OnPrecioCompraChange(val precio: String) : ProductosEvent
    data class OnPrecioVentaChange(val precio: String) : ProductosEvent
    data class OnStockChange(val stock: String) : ProductosEvent

    object GuardarProducto : ProductosEvent
    object BorrarProducto : ProductosEvent
}