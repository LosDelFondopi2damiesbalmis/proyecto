package com.proyecto.PeluPos.models

import com.google.gson.annotations.SerializedName

enum class RolUsuario {
    ADMINISTRADOR,
    MANAGER,
    EMPLEADO
}
data class Usuario(
    val idUsuario: Long,
    var usuario: String,
    var contrasena: String,
    @SerializedName("idEmpleado")
    val empleado: Empleado? = null,
    val rolUsuario: RolUsuario
)