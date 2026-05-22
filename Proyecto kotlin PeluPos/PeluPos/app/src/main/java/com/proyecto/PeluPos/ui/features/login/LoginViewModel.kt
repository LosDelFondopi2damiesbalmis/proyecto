package com.proyecto.PeluPos.ui.features.login

import android.net.http.HttpException
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
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

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
                println(listaActualizada)
                _uiState.update {
                    it.copy(usuariosDisponibles = listaActualizada, isLoading = false)
                }
            } catch (e: retrofit2.HttpException) {
                // 🔴 1. El servidor respondió, pero con un error (401, 403, 404, 500...)
                val mensajeError = when (e.code()) {
                    401 -> "No autorizado: Falta el token JWT o ha caducado"
                    403 -> "Prohibido: No tienes permisos para ver esto"
                    500 -> "Error en el servidor de Java"
                    else -> "Error HTTP: ${e.code()}"
                }
                println("🚨 ERROR HTTP: $mensajeError")
                _uiState.update { it.copy(isLoading = false, errorMessage = mensajeError) }

            } catch (e: IOException) {
                // 🟡 2. Error de red (No hay internet, Timeout, el servidor Java está apagado)
                println("🚨 ERROR DE RED: Comprueba tu conexión o si el servidor está encendido. Detalles: ${e.message}")
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Error de conexión con el servidor")
                }

            } catch (e: Exception) {
                // 🟢 3. Excepción de Corrutinas (¡IMPORTANTE RENOVARLA!)
                if (e is CancellationException) throw e

                // 🟣 4. Cualquier otro error (Ej. Gson explotó al leer el JSON)
                println("🚨 ERROR DESCONOCIDO: ${e.message}")
                e.printStackTrace()
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Error inesperado: ${e.message}")
                }
            }

        }
    }

    private fun verificarLogin(usuario: Usuario?, contrasena: String) {
        // 1. Validaciones locales rápidas
        if (usuario == null) {
            _uiState.update { it.copy(errorMessage = "Por favor, selecciona un usuario.") }
            return
        }

        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorMessage = "La contraseña no puede estar vacía.") }
            return
        }

        // 2. Petición a Tomcat usando Retrofit
        viewModelScope.launch {
            // Activamos isLoading = true para que tu pantalla muestre un CircularProgressIndicator
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // Extraemos el nombre de usuario (ej: "david")
                val nombreUsuarioString = usuario.usuario ?: ""

                // Llamamos a Retrofit. La app espera aquí a que Tomcat responda.
                // El propio sessionRepository.login() ya guarda el Token en memoria si va bien.
                sessionRepository.login(nombreUsuarioString, contrasena)

                // ¡Éxito! Tomcat ha devuelto un 200 OK
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        isLoginSuccessful = true
                    )
                }

            } catch (e: ApiServicesException) {
                // ¡Fallo! Tomcat dice que la contraseña está mal o el usuario no existe
                // Tu AuthServiceImplementation ya formatea el texto exacto del error
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }

            } catch (e: Exception) {
                // Fallo del móvil (ej: sin WiFi, Tomcat apagado, IP incorrecta)
                _uiState.update {
                    it.copy(errorMessage = "Error de conexión: Verifica tu internet o el servidor.", isLoading = false)
                }
            }
        }
    }
}