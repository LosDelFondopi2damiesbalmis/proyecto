package com.proyecto.PeluPos.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.SessionRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
import com.proyecto.PeluPos.models.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        cargarUsuariosReales()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> verificarLogin(event.usuario, event.contrasena)
            LoginEvent.OnErrorDismissed -> _uiState.update { it.copy(errorMessage = null) }
            LoginEvent.CargarUsuarios -> cargarUsuariosReales()
        }
    }

    private fun cargarUsuariosReales() {
        // Envolvemos en viewModelScope por si tu repositorio usa corrutinas (suspend fun)
        viewModelScope.launch {
            // Llama a tu función real del repositorio (ajusta el nombre si en el tuyo se llama getAll() o similar)
            val listaActualizada = usuarioRepository.getUsuarios()

            _uiState.update {
                it.copy(usuariosDisponibles = listaActualizada)
            }
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
}