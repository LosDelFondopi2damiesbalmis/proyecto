package com.proyecto.PeluPos.ui.features.servicios

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Servicio

data class ServiciosUiState(
    val todosLosServicios: List<Servicio> = emptyList(),
    val serviciosVisibles: List<Servicio> = emptyList(),
    val searchQuery: String = "",

    // Para el desplegable
    val empleadosDisponibles: List<Empleado> = emptyList(),

    // Formulario
    val editandoServicioId: Long? = null,
    val formNombre: String = "",
    val formPrecio: String = "",
    val formDescripcion: String = "",
    val formEmpleadoSeleccionado: Empleado? = null,

    // 🚀 NUEVO: Banderas de control de red para Retrofit
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = formNombre.isNotBlank() &&
                formPrecio.toDoubleOrNull() != null &&
                formEmpleadoSeleccionado != null
}