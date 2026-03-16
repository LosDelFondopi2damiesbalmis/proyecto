package com.proyecto.PeluPos.ui.features.usuarios

import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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


    private fun cargarDatos() {
        _uiState.update {
            it.copy(

                listaUsuarios = usuarioRepository.getUsuarios(),
                empleadosDisponibles = empleadoRepository.getEmpleados()
            )
        }
    }

    fun onEvent(event: UsuariosEvent) {
        when (event) {
            UsuariosEvent.CargarUsuarios -> cargarDatos()

            UsuariosEvent.PrepararNuevoUsuario -> {
                _uiState.update {
                    it.copy(
                        editandoUsuarioId = null,
                        formNombreUsuario = "",
                        formContrasena = "",
                        formRol = RolUsuario.EMPLEADO,
                        formEmpleadoSeleccionado = null
                    )
                }
            }
            is UsuariosEvent.PrepararEdicion -> { val usuarioAEditar = _uiState.value.listaUsuarios.find { it.idUsuario == event.idUsuario }
                usuarioAEditar?.let { user ->
                    _uiState.update {
                        it.copy(
                            editandoUsuarioId = user.idUsuario,
                            formNombreUsuario = user.usuario,
                            formContrasena = "", // Por seguridad, no cargamos la contraseña antigua en el campo visual
                            formRol = user.rolUsuario,
                            formEmpleadoSeleccionado = user.empleado
                        )
                    }
                } }

            is UsuariosEvent.OnNombreUsuarioChange -> _uiState.update { it.copy(formNombreUsuario = event.nombre) }
            is UsuariosEvent.OnContrasenaChange -> _uiState.update { it.copy(formContrasena = event.contrasena) }
            is UsuariosEvent.OnRolChange -> _uiState.update { it.copy(formRol = event.rol) }
            is UsuariosEvent.OnEmpleadoChange -> _uiState.update { it.copy(formEmpleadoSeleccionado = event.empleado) }

            UsuariosEvent.GuardarUsuario -> guardarUsuario()
        }
    }


    private fun guardarUsuario() {
        val currentState = _uiState.value
        val empleado = currentState.formEmpleadoSeleccionado ?: return


        val contraseñaFinal = if (currentState.editandoUsuarioId != null && currentState.formContrasena.isBlank()) {
            val usuarioAntiguo = usuarioRepository.getUsuario(currentState.editandoUsuarioId)
            usuarioAntiguo?.contrasena ?: ""
        } else {
            currentState.formContrasena
        }

        val nuevoUsuario = Usuario(
            idUsuario = currentState.editandoUsuarioId
                ?: System.currentTimeMillis(),
            usuario = currentState.formNombreUsuario,
            contrasena = contraseñaFinal,
            empleado = empleado,
            rolUsuario = currentState.formRol
        )


        if (currentState.editandoUsuarioId != null) {
            usuarioRepository.updateUsuario(nuevoUsuario)
        } else {
            usuarioRepository.insert(nuevoUsuario)
        }


        cargarDatos()
    }
}