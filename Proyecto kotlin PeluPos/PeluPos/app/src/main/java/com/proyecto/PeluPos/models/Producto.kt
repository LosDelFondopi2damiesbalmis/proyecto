package com.proyecto.PeluPos.models
data class Producto(
    val idProducto: Long,
    var nombre: String,
    var precioCompra: Double,
    var precioVenta: Double,
    var stock: Int
)