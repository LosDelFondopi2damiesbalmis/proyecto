package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.ProductoEntity
import com.proyecto.PeluPos.models.Producto

fun Producto.toEntity() = ProductoEntity(
    idProducto = idProducto,
    nombre = nombre,
    precioCompra = precioCompra,
    precioVenta = precioVenta,
    stock = stock
)

fun ProductoEntity.toModel() = Producto(
    idProducto = idProducto,
    nombre = nombre,
    precioCompra = precioCompra,
    precioVenta = precioVenta,
    stock = stock
)