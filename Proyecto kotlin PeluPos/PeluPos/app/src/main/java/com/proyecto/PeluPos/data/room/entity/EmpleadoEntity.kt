package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empleados")
data class EmpleadoEntity(
    @PrimaryKey(autoGenerate = true) val idEmpleado: Long = 0L,
    val telefono: Long = 0L,
    val email: String = "",
    val cargo: String = "",
    val nombre: String = "",
    val localId: Long? = null
)