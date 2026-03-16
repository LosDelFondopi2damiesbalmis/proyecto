package com.proyecto.PeluPos.ui.features.locales

import com.proyecto.PeluPos.models.Empleado

sealed interface LocalesEvent {
    object CargarDatos : LocalesEvent
    data class OnSearchQueryChange(val query: String) : LocalesEvent

    // Formulario
    object PrepararNuevoLocal : LocalesEvent
    data class PrepararEdicion(val idLocal: Long) : LocalesEvent
    data class OnNombreChange(val nombre: String) : LocalesEvent
    data class OnDireccionChange(val direccion: String) : LocalesEvent
    data class OnAddEmpleado(val empleado: Empleado) : LocalesEvent
    data class OnRemoveEmpleado(val empleado: Empleado) : LocalesEvent

    object GuardarLocal : LocalesEvent
    object BorrarLocal : LocalesEvent
}