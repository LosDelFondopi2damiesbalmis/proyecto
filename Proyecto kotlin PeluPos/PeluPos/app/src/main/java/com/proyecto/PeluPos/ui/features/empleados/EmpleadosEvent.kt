package com.proyecto.PeluPos.ui.features.empleados

import com.proyecto.PeluPos.models.Local

sealed interface EmpleadosEvent {
    object CargarDatos : EmpleadosEvent

    // Formulario
    object PrepararNuevoEmpleado : EmpleadosEvent
    data class PrepararEdicion(val idEmpleado: Long) : EmpleadosEvent

    data class OnNombreChange(val nombre: String) : EmpleadosEvent
    data class OnCargoChange(val cargo: String) : EmpleadosEvent
    data class OnEmailChange(val email: String) : EmpleadosEvent
    data class OnTelefonoChange(val telefono: String) : EmpleadosEvent
    data class OnLocalChange(val local: Local?) : EmpleadosEvent

    object GuardarEmpleado : EmpleadosEvent
    object BorrarEmpleado : EmpleadosEvent
}