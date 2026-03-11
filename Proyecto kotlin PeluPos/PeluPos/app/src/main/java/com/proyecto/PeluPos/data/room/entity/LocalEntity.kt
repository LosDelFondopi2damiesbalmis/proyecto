package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locales")
data class LocalEntity(
    @PrimaryKey(autoGenerate = true)
    val idLocal: Long = 0L,
    val nombre: String,
    val direccion: String
)