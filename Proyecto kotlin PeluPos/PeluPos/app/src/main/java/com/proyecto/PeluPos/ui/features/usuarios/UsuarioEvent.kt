package com.proyecto.PeluPos.ui.features.usuarios

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.RolUsuario

sealed interface UsuariosEvent {

    object CargarUsuarios : UsuariosEvent


    object PrepararNuevoUsuario : UsuariosEvent
    data class PrepararEdicion(val idUsuario: Long) : UsuariosEvent


    data class OnNombreUsuarioChange(val nombre: String) : UsuariosEvent
    data class OnContrasenaChange(val contrasena: String) : UsuariosEvent
    data class OnRolChange(val rol: RolUsuario) : UsuariosEvent
    data class OnEmpleadoChange(val empleado: Empleado) : UsuariosEvent

    object GuardarUsuario : UsuariosEvent
}