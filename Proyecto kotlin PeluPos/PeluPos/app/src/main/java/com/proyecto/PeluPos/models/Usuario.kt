package com.proyecto.PeluPos.models
enum class RolUsuario {
    ADMINISTRADOR,
    MANAGER,
    EMPLEADO
}
data class Usuario(
    val idUsuario: Long,
    var usuario: String,
    var contrasena: String,
    val empleado: Empleado,
    val rolUsuario: RolUsuario
)