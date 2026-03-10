package com.proyecto.PeluPos.ui.features.login

import com.proyecto.PeluPos.models.Usuario

sealed interface LoginEvent {
    data class OnLoginClick(val usuario: Usuario?, val contrasena: String) : LoginEvent
    object OnErrorDismissed : LoginEvent
    object CargarUsuarios : LoginEvent
}