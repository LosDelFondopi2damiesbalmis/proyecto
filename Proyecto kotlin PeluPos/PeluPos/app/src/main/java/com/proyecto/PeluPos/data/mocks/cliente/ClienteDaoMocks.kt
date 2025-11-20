package com.proyecto.PeluPos.data.mocks.cliente

import com.proyecto.PeluPos.data.mocks.ClienteMock

class ClienteDaoMock {

    private val clientes = mutableListOf(
        ClienteMock(1, "Juan Pérez", 150.0, 600111111),
        ClienteMock(2, "Ana García", 0.0, 600111112),
        ClienteMock(3, "Luis Martínez", 20.0, 600111113)
    )

    fun getAll(): List<ClienteMock> = clientes.toList()

    fun get(id: Long): ClienteMock? =
        clientes.find { it.idCliente == id }

    fun insert(c: ClienteMock): Boolean {
        (1L..clientes.size + 1L).forEach { i ->
            if (clientes.none { it.idCliente == i }) {
                clientes.add(c.copy(idCliente = i))
                return true
            }
        }
        return false
    }

    fun update(c: ClienteMock): Boolean {
        val index = clientes.indexOfFirst { it.idCliente == c.idCliente }
        if (index == -1) return false
        clientes[index] = c
        return true
    }

    fun delete(id: Long): Boolean {
        val index = clientes.indexOfFirst { it.idCliente == id }
        if (index == -1) return false
        clientes.removeAt(index)
        return true
    }
}

