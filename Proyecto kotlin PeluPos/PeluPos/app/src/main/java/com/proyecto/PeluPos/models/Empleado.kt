package com.proyecto.PeluPos.models

data class Empleado(
    val idEmpleado: Long = 0L,
    val telefono: Long = 0L,
    val email: String = "",
    val cargo: String = "",
    val nombre: String = "",
    val local: Local? = null
)