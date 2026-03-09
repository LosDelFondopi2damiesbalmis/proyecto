package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "facturas")
data class FacturaEntity(
    @PrimaryKey(autoGenerate = true) val idFactura: Long = 0L,
    var monto: Double = 0.0,
    val fecha: Long = System.currentTimeMillis(),
    var pendiente: Boolean = true,
    var tipoPago: String = "",
    val clienteId: Long,
    val empleadoId: Long
)