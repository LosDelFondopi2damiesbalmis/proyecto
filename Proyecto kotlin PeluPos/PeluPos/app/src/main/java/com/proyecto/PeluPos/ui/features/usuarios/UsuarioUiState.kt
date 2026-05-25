package com.proyecto.PeluPos.ui.features.usuarios

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario

data class UsuariosUiState(
    // Datos para la lista
    val listaUsuarios: List<Usuario> = emptyList(),

    // Datos para el formulario
    val empleadosDisponibles: List<Empleado> = emptyList(),

    // Campos del formulario
    val editandoUsuarioId: Long? = null,
    val formNombreUsuario: String = "",
    val formContrasena: String = "",
    val formRol: RolUsuario = RolUsuario.EMPLEADO,
    val formEmpleadoSeleccionado: Empleado? = null,

    // NUEVO: Banderas de control para las peticiones a Tomcat
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
) {
    // Computamos la validación directamente en el estado
    val isFormValid: Boolean
        get() = formEmpleadoSeleccionado != null && formNombreUsuario.isNotBlank()
}