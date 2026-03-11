package com.proyecto.PeluPos.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.SessionRepository
import com.proyecto.PeluPos.data.room.dao.EmpleadoDao
import com.proyecto.PeluPos.data.room.dao.UsuarioDao
import com.proyecto.PeluPos.data.room.entity.toModel
import com.proyecto.PeluPos.models.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.proyecto.PeluPos.data.mappers.toModel
import com.proyecto.PeluPos.models.Empleado

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val empleadoDao: EmpleadoDao,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        cargarUsuariosDesdeDB()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginClick -> verificarLogin(event.usuario, event.contrasena)
            LoginEvent.OnErrorDismissed -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun verificarLogin(usuario: Usuario?, contrasena: String) {
        if (usuario == null) {
            _uiState.update { it.copy(errorMessage = "Por favor, selecciona un usuario.") }
            return
        }

        if (contrasena.isBlank()) {
            _uiState.update { it.copy(errorMessage = "La contraseña no puede estar vacía.") }
            return
        }

        if (usuario.contrasena == contrasena) {
            sessionRepository.iniciarSesion(usuario)
            _uiState.update { it.copy(errorMessage = null, isLoginSuccessful = true) }
        } else {
            _uiState.update { it.copy(errorMessage = "Contraseña incorrecta.") }
        }
    }

    private fun cargarUsuariosDesdeDB() {
        viewModelScope.launch {
            usuarioDao.getAllFlow().collect { listaUsuariosEntity ->
                val listaUsuariosModel: List<Usuario> = listaUsuariosEntity.mapNotNull { usuarioEntity ->
                    // Obtenemos el EmpleadoEntity de la base de datos
                    val empleadoEntity = empleadoDao.getById(usuarioEntity.empleadoId)
                    // Convertimos a modelo de dominio
                    val empleado: Empleado? = empleadoEntity?.toModel()
                    // Solo si existe, mapeamos a Usuario
                    empleado?.let { e -> usuarioEntity.toModel(e) }
                }
                _uiState.update { currentState ->
                    currentState.copy(usuariosDisponibles = listaUsuariosModel)
                }
            }
        }
    }
}