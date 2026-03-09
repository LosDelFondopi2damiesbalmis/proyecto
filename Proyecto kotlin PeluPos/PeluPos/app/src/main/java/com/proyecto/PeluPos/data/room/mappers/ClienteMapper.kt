package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.ClienteEntity
import com.proyecto.PeluPos.models.Cliente

fun Cliente.toEntity() = ClienteEntity(
    idCliente = idCliente,
    nombre = nombre,
    deuda = deuda,
    telefono = telefono
)

fun ClienteEntity.toModel() = Cliente(
    idCliente = idCliente,
    nombre = nombre,
    deuda = deuda,
    telefono = telefono
)