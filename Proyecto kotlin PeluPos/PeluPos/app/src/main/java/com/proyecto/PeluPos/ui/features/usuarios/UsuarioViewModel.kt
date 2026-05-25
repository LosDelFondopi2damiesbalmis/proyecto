package com.proyecto.PeluPos.ui.features.usuarios

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario
import com.proyecto.PeluPos.navigation.UsuarioFormRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsuariosViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val empleadoRepository: EmpleadoRepository,
    private val savedStateHandle: SavedStateHandle // 🚀 1. INYECTAMOS LA ANTENA
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuariosUiState())
    val uiState: StateFlow<UsuariosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    // --------------------------------------------------------
    // 1. CARGAR DATOS (Ahora lee la ruta dinámicamente)
    // --------------------------------------------------------
    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                // Descargamos de Tomcat simultáneamente
                val usuariosApi = usuarioRepository.getUsuarios()
                val empleadosApi = empleadoRepository.getEmpleados()

                // 🚀 2. CAPTURAMOS EL ID DE LA RUTA EN ESTE MOMENTO EXACTO
                // Asegúrate de importar tu UsuarioFormRoute aquí
                val idDesdeRuta = savedStateHandle.toRoute<UsuarioFormRoute>().idUsuario

                // 🚀 3. PREPARAMOS EL ESTADO BASE
                var newState = _uiState.value.copy(
                    listaUsuarios = usuariosApi,
                    empleadosDisponibles = empleadosApi,
                    isLoading = false
                )

                // 🚀 4. ¡LA MAGIA DE LA EDICIÓN AUTOMÁTICA!
                if (idDesdeRuta != null) {
                    val user = usuariosApi.find { it.idUsuario == idDesdeRuta }
                    if (user != null) {
                        newState = newState.copy(
                            editandoUsuarioId = user.idUsuario,
                            formNombreUsuario = user.usuario ?: "",
                            formContrasena = "", // Por seguridad, no cargamos la contraseña visualmente
                            formRol = user.rolUsuario ?: RolUsuario.EMPLEADO,
                            formEmpleadoSeleccionado = user.empleado
                        )
                    }
                } else {
                    // MODO "CREAR": Limpiamos los campos para arrancar en blanco de forma segura
                    newState = newState.copy(
                        editandoUsuarioId = null,
                        formNombreUsuario = "",
                        formContrasena = "",
                        formRol = RolUsuario.EMPLEADO,
                        formEmpleadoSeleccionado = null
                    )
                }

                _uiState.value = newState

            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // --------------------------------------------------------
    // 2. EVENTOS (Zombies eliminados)
    // --------------------------------------------------------
    fun onEvent(event: UsuariosEvent) {
        when (event) {
            UsuariosEvent.CargarUsuarios -> cargarDatos()

            // 🧹 ESTOS EVENTOS YA SON ZOMBIS. Ya no hace falta dispararlos desde la UI.
            UsuariosEvent.PrepararNuevoUsuario -> { }
            is UsuariosEvent.PrepararEdicion -> { }

            is UsuariosEvent.OnNombreUsuarioChange -> _uiState.update { it.copy(formNombreUsuario = event.nombre) }
            is UsuariosEvent.OnContrasenaChange -> _uiState.update { it.copy(formContrasena = event.contrasena) }
            is UsuariosEvent.OnRolChange -> _uiState.update { it.copy(formRol = event.rol) }
            is UsuariosEvent.OnEmpleadoChange -> _uiState.update { it.copy(formEmpleadoSeleccionado = event.empleado) }

            UsuariosEvent.GuardarUsuario -> guardarUsuario()
        }
    }

    // --------------------------------------------------------
    // 3. GUARDAR (POST / PUT)
    // --------------------------------------------------------
    private fun guardarUsuario() {
        val currentState = _uiState.value
        val empleado = currentState.formEmpleadoSeleccionado

        if (empleado == null) {
            _uiState.update { it.copy(error = "Debes seleccionar un empleado") }
            return
        }

        // Recuperamos la contraseña vieja desde la memoria si estamos editando y el campo está en blanco
        val contraseñaFinal = if (currentState.editandoUsuarioId != null && currentState.formContrasena.isBlank()) {
            val usuarioAntiguo = currentState.listaUsuarios.find { it.idUsuario == currentState.editandoUsuarioId }
            usuarioAntiguo?.contrasena ?: ""
        } else {
            currentState.formContrasena
        }

        val nuevoUsuario = Usuario(
            idUsuario = currentState.editandoUsuarioId ?: 0L,
            usuario = currentState.formNombreUsuario,
            contrasena = contraseñaFinal,
            empleado = empleado,
            rolUsuario = currentState.formRol
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                val mensajeExito = if (currentState.editandoUsuarioId != null) {
                    usuarioRepository.actualizarUsuario(nuevoUsuario)
                } else {
                    usuarioRepository.crearUsuario(nuevoUsuario)
                }

                _uiState.update { it.copy(mensaje = mensajeExito) }

                // Recargamos la lista. Al ejecutarse 'cargarDatos()', si la ruta ya no tiene el ID,
                // el propio flujo limpiará el estado del formulario automáticamente.
                cargarDatos()

            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}