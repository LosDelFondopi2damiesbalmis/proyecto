package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.LocalEntity
import com.proyecto.PeluPos.models.Local
import com.proyecto.PeluPos.models.Empleado

fun Local.toEntity() = LocalEntity(
    idLocal = idLocal,
    nombre = nombre,
    direccion = direccion
)

fun LocalEntity.toModel(empleados: MutableList<Empleado> = mutableListOf()) = Local(
    idLocal = idLocal,
    nombre = nombre,
    direccion = direccion,
    empleados = empleados
)