package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicios")
data class ServicioEntity(
    @PrimaryKey(autoGenerate = true) val idServicio: Long = 0L,
    var nombre: String,
    var precio: Double,
    var descripcion: String,
    val empleadoId: Long
)