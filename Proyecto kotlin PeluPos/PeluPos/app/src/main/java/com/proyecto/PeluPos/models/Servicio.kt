package com.proyecto.PeluPos.models

import com.google.gson.annotations.SerializedName

data class Servicio(
    val idServicio: Long,
    var nombre: String,
    var precio: Double,
    var descripcion: String,
    @SerializedName("idEmpleado")
    val empleado: Empleado
)