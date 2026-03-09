package com.proyecto.PeluPos.data.room.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class LocalWithEmpleados(
    @Embedded val local: LocalEntity,
    @Relation(
        parentColumn = "idLocal",
        entityColumn = "idEmpleado",
        associateBy = Junction(LocalEmpleadoCrossRef::class)
    )
    val empleados: List<EmpleadoEntity>
)