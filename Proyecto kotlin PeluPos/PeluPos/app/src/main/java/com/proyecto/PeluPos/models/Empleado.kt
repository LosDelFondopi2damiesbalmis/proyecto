package com.proyecto.PeluPos.models
data class Empleado(
    val idEmpleado: Long,
    var telefono: Long,
    var email: String,
    var cargo: String,
    var nombre: String,
    var local: Local? = null
)