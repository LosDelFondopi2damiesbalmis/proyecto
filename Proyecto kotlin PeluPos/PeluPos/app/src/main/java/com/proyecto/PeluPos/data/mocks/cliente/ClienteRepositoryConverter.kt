package com.proyecto.PeluPos.data.mocks.cliente

import com.proyecto.PeluPos.data.mocks.ClienteMock
import com.proyecto.PeluPos.models.Cliente


fun Cliente.toClienteMock() =
    ClienteMock(idCliente, nombre, deuda, telefono)

fun List<Cliente>.toClientesMock() =
    map { it.toClienteMock() }

fun ClienteMock.toCliente() =
    Cliente(idCliente, nombre, deuda, telefono)

fun List<ClienteMock>.toClientes() =
    map { it.toCliente() }
