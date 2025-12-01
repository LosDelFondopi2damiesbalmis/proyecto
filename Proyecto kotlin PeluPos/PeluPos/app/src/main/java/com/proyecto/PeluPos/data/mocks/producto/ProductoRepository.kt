package com.proyecto.PeluPos.data.mocks.producto

import com.proyecto.PeluPos.models.Producto

class ProductoRepository() {
    private val productoDaoMock: ProductoDaoMock = ProductoDaoMock()
    fun getProductos(): List<Producto> =
        productoDaoMock.getAll().toProductos()

    fun getProducto(id: Long): Producto? =
        productoDaoMock.get(id)?.toProducto()

    fun insert(producto: Producto): Boolean =
        productoDaoMock.insert(producto.toProductoMock())

    fun updateProducto(producto: Producto): Boolean =
        productoDaoMock.update(producto.toProductoMock())

    fun delete(id: Long): Boolean =
        productoDaoMock.delete(id)
}
