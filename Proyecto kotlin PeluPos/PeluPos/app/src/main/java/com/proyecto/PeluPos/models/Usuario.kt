package com.proyecto.PeluPos.models
data class Usuario(
    val idUsuario: Long,
    var usuario: String,
    var contrasena: String,
    val empleado: Empleado
)