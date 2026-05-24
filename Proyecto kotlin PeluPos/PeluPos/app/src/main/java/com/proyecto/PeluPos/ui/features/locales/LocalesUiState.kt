package com.proyecto.PeluPos.ui.features.locales

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

data class LocalesUiState(
    // Listas
    val todosLosLocales: List<Local> = emptyList(),
    val localesVisibles: List<Local> = emptyList(),
    val searchQuery: String = "",

    // Empleados de la base de datos (para el selector)
    val empleadosDisponibles: List<Empleado> = emptyList(),

    // Formulario
    val editandoLocalId: Long? = null,
    val formNombre: String = "",
    val formDireccion: String = "",
    val formEmpleadosSeleccionados: List<Empleado> = emptyList(),

    // 🚀 NUEVO: Control de estados de red para Retrofit
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = formNombre.isNotBlank() && formDireccion.isNotBlank()
}