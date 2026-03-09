package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity

@Entity(primaryKeys = ["localId", "empleadoId"])
data class LocalEmpleadoCrossRef(
    val localId: Long,
    val empleadoId: Long
)