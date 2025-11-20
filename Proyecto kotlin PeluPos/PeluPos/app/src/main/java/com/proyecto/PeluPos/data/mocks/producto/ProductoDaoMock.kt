package com.proyecto.PeluPos.data.mocks.producto

import com.proyecto.PeluPos.data.mocks.ProductoMock

class ProductoDaoMock {

    private val productos = mutableListOf(
        ProductoMock(1, "Laptop X", 500.0, 750.0, 10),
        ProductoMock(2, "Mouse Óptico", 5.0, 12.0, 50),
        ProductoMock(3, "Teclado Mecánico", 20.0, 45.0, 30)
    )

    fun getAll(): List<ProductoMock> = productos.toList()

    fun get(id: Long): ProductoMock? =
        productos.find { it.idProducto == id }

    fun insert(p: ProductoMock): Boolean {
        (1L..productos.size + 1L).forEach { i ->
            if (productos.none { it.idProducto == i }) {
                productos.add(p.copy(idProducto = i))
                return true
            }
        }
        return false
    }

    fun update(p: ProductoMock): Boolean {
        val index = productos.indexOfFirst { it.idProducto == p.idProducto }
        if (index == -1) return false
        productos[index] = p
        return true
    }

    fun delete(id: Long): Boolean {
        val index = productos.indexOfFirst { it.idProducto == id }
        if (index == -1) return false
        productos.removeAt(index)
        return true
    }
}
