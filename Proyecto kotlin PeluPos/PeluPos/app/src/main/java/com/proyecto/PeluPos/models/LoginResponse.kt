package com.proyecto.PeluPos.models

data class LoginResponse(
    val jwtToken: String? = null,
    val mensaje: String? = null,      // Para cuando hay errores
    val idUsuario: String? = null,
    val usuario: String? = null,
    val rolUsuario: String? = null,
    val idEmpleado: String? = null
)