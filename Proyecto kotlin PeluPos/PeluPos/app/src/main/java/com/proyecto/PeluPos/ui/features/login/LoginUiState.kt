package com.proyecto.PeluPos.ui.features.login

import com.proyecto.PeluPos.models.Usuario

data class LoginUiState(
    val usuariosDisponibles: List<Usuario> = emptyList(),
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false // Bandera para saber cuándo navegar
)