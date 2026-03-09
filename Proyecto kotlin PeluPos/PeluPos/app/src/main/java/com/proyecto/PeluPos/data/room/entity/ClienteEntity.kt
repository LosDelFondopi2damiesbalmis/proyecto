package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val idCliente: Long = 0L,
    var nombre: String,
    var deuda: Double,
    var telefono: Long
)