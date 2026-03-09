package com.proyecto.PeluPos.data.room.entity

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

// De modelo a entity
fun Local.toEntity() = LocalEntity(
    idLocal = idLocal,
    nombre = nombre,
    direccion = direccion
)

// De entity con empleados a modelo
fun LocalWithEmpleados.toModel(): Local {
    return Local(
        idLocal = local.idLocal,
        nombre = local.nombre,
        direccion = local.direccion,
        empleados = empleados.map {
            Empleado(
                idEmpleado = it.idEmpleado,
                nombre = it.nombre,
                telefono = it.telefono,
                email = it.email,
                cargo = it.cargo,
                local = null // evitar ciclo, opcional
            )
        }.toMutableList()
    )
}

// De modelo a CrossRef para Room
fun Local.toCrossRefs(): List<LocalEmpleadoCrossRef> {
    return empleados.map { empleado ->
        LocalEmpleadoCrossRef(
            localId = idLocal,
            empleadoId = empleado.idEmpleado
        )
    }
}