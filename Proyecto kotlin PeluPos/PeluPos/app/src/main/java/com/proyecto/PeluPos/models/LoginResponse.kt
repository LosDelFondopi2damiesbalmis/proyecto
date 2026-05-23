package com.proyecto.PeluPos.models

data class LoginResponse(
    val jwtToken: String?,
    val idUsuario: String?,
    val usuario: String?,
    val rolUsuario: String?,
    val idEmpleado: String?,
    val mensaje: String
)