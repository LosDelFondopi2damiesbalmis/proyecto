package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.EmpleadoEntity
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

fun Empleado.toEntity() = EmpleadoEntity(
    idEmpleado = idEmpleado,
    telefono = telefono,
    email = email,
    cargo = cargo,
    nombre = nombre,
    localId = local?.idLocal
)

fun EmpleadoEntity.toModel(local: Local? = null) = Empleado(
    idEmpleado = idEmpleado,
    telefono = telefono,
    email = email,
    cargo = cargo,
    nombre = nombre,
    local = local
)