package com.proyecto.PeluPos.ui.features.servicios

import com.proyecto.PeluPos.models.Empleado

sealed interface ServiciosEvent {
    object CargarDatos : ServiciosEvent
    data class OnSearchQueryChange(val query: String) : ServiciosEvent

    // Formulario
    object PrepararNuevoServicio : ServiciosEvent
    data class PrepararEdicion(val idServicio: Long) : ServiciosEvent
    data class OnNombreChange(val nombre: String) : ServiciosEvent
    data class OnPrecioChange(val precio: String) : ServiciosEvent
    data class OnDescripcionChange(val descripcion: String) : ServiciosEvent
    data class OnEmpleadoChange(val empleado: Empleado) : ServiciosEvent

    object GuardarServicio : ServiciosEvent
    object BorrarServicio : ServiciosEvent
}