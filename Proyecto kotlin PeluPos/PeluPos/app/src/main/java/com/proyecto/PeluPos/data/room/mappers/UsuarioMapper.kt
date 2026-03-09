package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.UsuarioEntity
import com.proyecto.PeluPos.models.Usuario
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.RolUsuario

// De modelo a entity
fun Usuario.toEntity() = UsuarioEntity(
    idUsuario = idUsuario,
    usuario = usuario,
    contrasena = contrasena,
    empleadoId = empleado.idEmpleado,
    rolUsuario = rolUsuario.name
)

// De entity a modelo
fun UsuarioEntity.toModel(empleado: Empleado) = Usuario(
    idUsuario = idUsuario,
    usuario = usuario,
    contrasena = contrasena,
    empleado = empleado,
    rolUsuario = RolUsuario.valueOf(rolUsuario)
)