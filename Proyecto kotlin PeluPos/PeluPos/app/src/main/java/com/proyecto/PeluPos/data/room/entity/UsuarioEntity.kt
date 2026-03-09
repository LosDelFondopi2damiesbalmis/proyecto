package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val idUsuario: Long = 0L,
    var usuario: String,
    var contrasena: String,
    val empleadoId: Long,
    val rolUsuario: String
)