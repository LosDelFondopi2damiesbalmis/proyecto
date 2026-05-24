package com.proyecto.PeluPos.data.mocks.cliente

import com.proyecto.PeluPos.data.services.clientes.ClienteService
import com.proyecto.PeluPos.models.Cliente
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class ClienteRepository @Inject constructor(
    private val clienteService: ClienteService
) {
    suspend fun getClientes(): List<Cliente> {
        val response = clienteService.getClientes()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error del servidor al obtener clientes: ${response.code()}")
        }
    }

    suspend fun getCliente(id: Long): Cliente? {
        val response = clienteService.getCliente(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error al buscar el cliente: ${response.code()}")
        }
    }

    suspend fun createCliente(cliente: Cliente): Cliente {
        val response = clienteService.createCliente(cliente)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo crear el cliente en el servidor")
        }
    }

    suspend fun updateCliente(id: Long, cliente: Cliente): Cliente {
        val response = clienteService.updateCliente(id, cliente)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo actualizar el cliente")
        }
    }

    suspend fun deleteCliente(id: Long) {
        val response = clienteService.deleteCliente(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo borrar el cliente")
        }
    }
}