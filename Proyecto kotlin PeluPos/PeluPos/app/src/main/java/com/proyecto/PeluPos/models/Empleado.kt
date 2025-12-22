package com.proyecto.PeluPos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "empleados")
data class Empleado(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val apellidos: String,
    val dni: String,
    val telefono: String,
    val email: String,
    val fechaContratacion: Date = Date(),
    val especialidades: List<String>, // ["Corte", "Coloración", "Extensiones"]
    val horario: HorarioTrabajo = HorarioTrabajo(null,null, null, null, null, null, null ),
    val salario: Double = 0.0,
    val tipoContrato: String, // "Fijo", "Temporal", "Autónomo"
    val activo: Boolean = true,
    val foto: String? = null,
    val notas: String = ""
)

data class HorarioTrabajo(
    val lunes: Pair<String, String>?, // "09:00" to "18:00"
    val martes: Pair<String, String>?,
    val miercoles: Pair<String, String>?,
    val jueves: Pair<String, String>?,
    val viernes: Pair<String, String>?,
    val sabado: Pair<String, String>?,
    val domingo: Pair<String, String>?
)