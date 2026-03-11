package com.proyecto.PeluPos.data.room.entity

import com.proyecto.PeluPos.models.Local

// Entity -> Modelo
fun LocalEntity.toModel(): Local {
    return Local(
        idLocal = idLocal,
        nombre = nombre,
        direccion = direccion,
        empleados = mutableListOf()
    )
}

// Modelo -> Entity
fun Local.toEntity(): LocalEntity {
    return LocalEntity(
        idLocal = idLocal,
        nombre = nombre,
        direccion = direccion
    )
}