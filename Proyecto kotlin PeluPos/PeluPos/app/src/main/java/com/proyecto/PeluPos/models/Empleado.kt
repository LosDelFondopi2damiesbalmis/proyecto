package com.proyecto.PeluPos.models

import com.google.gson.annotations.SerializedName

data class Empleado(
    val idEmpleado: Long,
    var telefono: Long,
    var email: String,
    var cargo: String,
    var nombre: String,
    @SerializedName("idLocal")
    var idLocal: Local? = null
)