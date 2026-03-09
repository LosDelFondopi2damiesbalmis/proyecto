package com.proyecto.PeluPos.ui.features.login

import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.SessionRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        cargarUsuariosFalsos() // Simulamos la carga desde la BD
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> verificarLogin(event.usuario, event.contrasena)
            LoginEvent.OnErrorDismissed -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun verificarLogin(usuario: Usuario?, contrasena: String) {
        // 1. Validar que haya seleccionado un usuario
        if (usuario == null) {
            _uiState.update { it.copy(errorMessage = "Por favor, selecciona un usuario.") }
            return
        }

        // 2. Validar que la contraseña no esté vacía
        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorMessage = "La contraseña no puede estar vacía.") }
            return
        }

        // 3. Comprobar credenciales (AQUÍ ES DONDE SUCEDE LA MAGIA)
        if (usuario.contrasena == contrasena) {
            // Si la cuenta está inactiva (como vimos en el modelo anterior), podrías bloquearlo aquí
            /* if (!usuario.activo) {
                 _uiState.update { it.copy(errorMessage = "Esta cuenta está desactivada.") }
                 return
               } */

            // ¡Éxito! Limpiamos errores y marcamos como exitoso
            sessionRepository.iniciarSesion(usuario)
            _uiState.update {
                it.copy(
                    errorMessage = null,
                    isLoginSuccessful = true
                )
            }
        } else {
            // ¡Fallo! Mostramos error
            _uiState.update { it.copy(errorMessage = "Contraseña incorrecta.") }
        }
    }

    private fun cargarUsuariosFalsos() {
        // Usa los mismos mocks que tenías en tu Preview
        val mockLocal = Local(1, "Sede Central", "Calle Mayor")
        val admin = Empleado(101, 600123456L, "ana@pelupos.com", "Gerente", "Ana García", mockLocal)
        val cajero = Empleado(102, 600654321L, "marcos@pelupos.com", "Estilista", "Marcos López", mockLocal)

        val lista = listOf(
            Usuario(1, "admin", "1234", admin, RolUsuario.ADMINISTRADOR),
            Usuario(2, "mlopez", "1234", cajero, RolUsuario.MANAGER)
        )

        _uiState.update { it.copy(usuariosDisponibles = lista) }
    }
}