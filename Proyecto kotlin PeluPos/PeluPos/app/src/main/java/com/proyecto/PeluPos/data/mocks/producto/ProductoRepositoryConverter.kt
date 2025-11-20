package com.proyecto.PeluPos.data.mocks.producto

import com.proyecto.PeluPos.data.mocks.ProductoMock
import com.proyecto.PeluPos.models.Producto

fun Producto.toProductoMock() =
    ProductoMock(idProducto, nombre, precioCompra, precioVenta, stock)

fun List<Producto>.toProductosMock() =
    map { it.toProductoMock() }

fun ProductoMock.toProducto() =
    Producto(idProducto, nombre, precioCompra, precioVenta, stock)

fun List<ProductoMock>.toProductos() =
    map { it.toProducto() }
