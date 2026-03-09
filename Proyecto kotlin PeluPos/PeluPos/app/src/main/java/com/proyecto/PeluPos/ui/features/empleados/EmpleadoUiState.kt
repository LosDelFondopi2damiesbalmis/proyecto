package com.proyecto.PeluPos.ui.features.empleados

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

data class EmpleadosUiState(
    val empleados: List<Empleado> = emptyList(),
    val localesDisponibles: List<Local> = emptyList(),

    // Estado del Formulario
    val editandoEmpleadoId: Long? = null,
    val formNombre: String = "",
    val formCargo: String = "",
    val formEmail: String = "",
    val formTelefono: String = "",
    val formLocalSeleccionado: Local? = null
) {
    val isFormValid: Boolean
        get() = formNombre.isNotBlank() && formCargo.isNotBlank()
}