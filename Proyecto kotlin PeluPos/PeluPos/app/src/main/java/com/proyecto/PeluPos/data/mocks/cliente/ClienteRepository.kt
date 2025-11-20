package com.proyecto.PeluPos.data.mocks.cliente

import com.proyecto.PeluPos.models.Cliente

class ClienteRepository() {
    private val clienteDaoMock: ClienteDaoMock = ClienteDaoMock()
    // Devuelve todos los clientes como objetos reales
    fun getClientes(): List<Cliente> =
        clienteDaoMock.getAll().toClientes() // usa tu extensión List<ClienteMock>.toClientes()

    // Devuelve un cliente por id
    fun getCliente(id: Long): Cliente? =
        clienteDaoMock.get(id)?.toCliente()

    // Inserta un cliente
    fun insert(cliente: Cliente): Boolean =
        clienteDaoMock.insert(cliente.toClienteMock())

    // Actualiza un cliente
    fun updateCliente(cliente: Cliente): Boolean =
        clienteDaoMock.update(cliente.toClienteMock())

    // Borra un cliente por id
    fun delete(id: Long): Boolean =
        clienteDaoMock.delete(id)
}
