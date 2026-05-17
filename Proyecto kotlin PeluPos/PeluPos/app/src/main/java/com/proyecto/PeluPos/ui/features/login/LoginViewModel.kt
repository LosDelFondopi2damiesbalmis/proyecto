package com.proyecto.PeluPos.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.SessionRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
import com.proyecto.PeluPos.models.Usuario
import com.proyecto.PeluPos.data.services.autentication.ApiServicesException
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
        viewModelScope.launch {
            try {
                val listaActualizada = usuarioRepository.getUsuarios()
                _uiState.update { it.copy(usuariosDisponibles = listaActualizada) }
            } catch (e: Exception) {
                // Si falla la carga inicial de la lista, lo ignoramos o mostramos error
            }
        }
    }

    private fun verificarLogin(usuario: Usuario?, contrasena: String) {
        // 1. Validar que haya seleccionado un usuario de la lista
        if (usuario == null) {
            _uiState.update { it.copy(errorMessage = "Por favor, selecciona un usuario.") }
            return
        }

        // 2. Validar que la contraseña no esté vacía
        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorMessage = "La contraseña no puede estar vacía.") }
            return
        }

        // 3. LA NUEVA MAGIA: Pedir permiso al servidor Tomcat
        viewModelScope.launch {
            try {
                // Extraemos el nombre de usuario en texto (ej: "luciaPedoPis")
                // Ajusta "usuario.usuario" si en tu clase el atributo se llama distinto (ej: usuario.nombre)
                val nombreUsuarioString = usuario.usuario

                // Llamamos a Retrofit. La app se "pausa" aquí una fracción de segundo esperando a Tomcat
                val response = sessionRepository.login(nombreUsuarioString, contrasena)

                // ¡Éxito! Tomcat ha devuelto un 200 OK y el Token JWT
                _uiState.update {
                    it.copy(
                        errorMessage = null,
                        isLoginSuccessful = true
                    )
                }

                // (Nota para más adelante: Aquí es donde guardaremos response.jwtToken en el móvil
                // para usarlo en el Interceptor de las siguientes pantallas).

            } catch (e: ApiServicesException) {
                // ¡Fallo! Tomcat nos dice que la contraseña está mal (Error 401)
                _uiState.update { it.copy(errorMessage = e.message) }
            } catch (e: Exception) {
                // Fallo del móvil (ej: Modo avión, sin internet, Tomcat apagado)
                _uiState.update { it.copy(errorMessage = "Error de conexión: Verifica tu internet o el servidor.") }
            }
        }
    }
}