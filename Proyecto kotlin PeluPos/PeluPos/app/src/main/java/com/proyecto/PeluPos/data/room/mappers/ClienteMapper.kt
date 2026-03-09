package com.proyecto.PeluPos.data.room.entity

import com.proyecto.PeluPos.data.room.entity.ClienteEntity
import com.proyecto.PeluPos.models.Cliente

// De modelo a entity
fun Cliente.toEntity() = ClienteEntity(
    idCliente = idCliente,
    nombre = nombre,
    telefono = telefono,
    deuda = deuda
)

// De entity a modelo
fun ClienteEntity.toModel() = Cliente(
    idCliente = idCliente,
    nombre = nombre,
    telefono = telefono,
    deuda = deuda
)