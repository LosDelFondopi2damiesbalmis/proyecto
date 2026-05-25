package com.proyecto.PeluPos.ui.features.usuarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario
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
    private val empleadoRepository: EmpleadoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UsuariosUiState())
    val uiState: StateFlow<UsuariosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    // --------------------------------------------------------
    // 1. CARGAR DATOS (Ahora con internet y Corrutinas)
    // --------------------------------------------------------
    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                // Descargamos de Tomcat simultáneamente
                val usuariosApi = usuarioRepository.getUsuarios()
                val empleadosApi = empleadoRepository.getEmpleados() // Asumo que esto ya lo pasaste a suspend fun

                _uiState.update {
                    it.copy(
                        listaUsuarios = usuariosApi,
                        empleadosDisponibles = empleadosApi,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // --------------------------------------------------------
    // 2. EVENTOS (Casi intactos, súper limpios)
    // --------------------------------------------------------
    fun onEvent(event: UsuariosEvent) {
        when (event) {
            UsuariosEvent.CargarUsuarios -> cargarDatos()

            UsuariosEvent.PrepararNuevoUsuario -> {
                _uiState.update {
                    it.copy(
                        editandoUsuarioId = null,
                        formNombreUsuario = "",
                        formContrasena = "",
                        formRol = RolUsuario.EMPLEADO, // Asumo que usas un Enum o Constante
                        formEmpleadoSeleccionado = null
                    )
                }
            }

            is UsuariosEvent.PrepararEdicion -> {
                val usuarioAEditar = _uiState.value.listaUsuarios.find { it.idUsuario == event.idUsuario }
                usuarioAEditar?.let { user ->
                    _uiState.update {
                        it.copy(
                            editandoUsuarioId = user.idUsuario,
                            formNombreUsuario = user.usuario ?: "",
                            formContrasena = "", // Por seguridad, no cargamos la contraseña antigua visualmente
                            formRol = user.rolUsuario ?: RolUsuario.EMPLEADO,
                            formEmpleadoSeleccionado = user.empleado
                        )
                    }
                }
            }

            is UsuariosEvent.OnNombreUsuarioChange -> _uiState.update { it.copy(formNombreUsuario = event.nombre) }
            is UsuariosEvent.OnContrasenaChange -> _uiState.update { it.copy(formContrasena = event.contrasena) }
            is UsuariosEvent.OnRolChange -> _uiState.update { it.copy(formRol = event.rol) }
            is UsuariosEvent.OnEmpleadoChange -> _uiState.update { it.copy(formEmpleadoSeleccionado = event.empleado) }

            UsuariosEvent.GuardarUsuario -> guardarUsuario()

            // Opcional para limpiar Toast
        }
    }

    // --------------------------------------------------------
    // 3. GUARDAR (POST / PUT)
    // --------------------------------------------------------
    private fun guardarUsuario() {
        val currentState = _uiState.value
        val empleado = currentState.formEmpleadoSeleccionado

        // Pequeña validación extra de seguridad
        if (empleado == null) {
            _uiState.update { it.copy(error = "Debes seleccionar un empleado") }
            return
        }

        // Recuperamos la contraseña vieja desde la memoria (listaUsuarios) en vez de llamar al Repo
        val contraseñaFinal = if (currentState.editandoUsuarioId != null && currentState.formContrasena.isBlank()) {
            val usuarioAntiguo = currentState.listaUsuarios.find { it.idUsuario == currentState.editandoUsuarioId }
            usuarioAntiguo?.contrasena ?: ""
        } else {
            currentState.formContrasena
        }

        val nuevoUsuario = Usuario(
            // Le añadimos el ?: 0L al final
            idUsuario = currentState.editandoUsuarioId ?: 0L,
            usuario = currentState.formNombreUsuario,
            contrasena = contraseñaFinal,
            empleado = empleado,
            rolUsuario = currentState.formRol
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                // Usamos los métodos de Retrofit que devuelven el String con el "Mensaje de éxito"
                val mensajeExito = if (currentState.editandoUsuarioId != null) {
                    usuarioRepository.actualizarUsuario(nuevoUsuario) // O updateUsuario si le dejaste ese nombre
                } else {
                    usuarioRepository.crearUsuario(nuevoUsuario)      // O insert si le dejaste ese nombre
                }

                // Limpiamos el formulario y avisamos a la pantalla
                _uiState.update {
                    it.copy(
                        mensaje = mensajeExito,
                        isLoading = false,
                        editandoUsuarioId = null,
                        formNombreUsuario = "",
                        formContrasena = "",
                        formRol = RolUsuario.EMPLEADO,
                        formEmpleadoSeleccionado = null
                    )
                }

                // Recargamos la lista actualizada de la base de datos
                cargarDatos()

            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}