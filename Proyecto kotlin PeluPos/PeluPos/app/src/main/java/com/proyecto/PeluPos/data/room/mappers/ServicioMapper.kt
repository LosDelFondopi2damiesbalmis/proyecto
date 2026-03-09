package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.ServicioEntity
import com.proyecto.PeluPos.models.Servicio
import com.proyecto.PeluPos.models.Empleado

// De modelo a entity
fun Servicio.toEntity() = ServicioEntity(
    idServicio = idServicio,
    nombre = nombre,
    precio = precio,
    descripcion = descripcion,
    empleadoId = empleado.idEmpleado
)

// De entity a modelo
fun ServicioEntity.toModel(empleado: Empleado) = Servicio(
    idServicio = idServicio,
    nombre = nombre,
    precio = precio,
    descripcion = descripcion,
    empleado = empleado
)