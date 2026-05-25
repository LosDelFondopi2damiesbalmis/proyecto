package com.proyecto.PeluPos.models
data class Local(
    val idLocal: Long,
    var nombre: String,
    var direccion: String,
    val empleadoCollection: List<Empleado> = emptyList()
)