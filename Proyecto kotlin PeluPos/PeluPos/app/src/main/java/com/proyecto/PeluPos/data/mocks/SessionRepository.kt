package com.proyecto.PeluPos.data.mocks

import com.proyecto.PeluPos.models.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor() {


    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    // Llama a esto cuando el login sea correcto
    fun iniciarSesion(usuario: Usuario) {
        _usuarioActual.value = usuario
    }

    // Llama a esto cuando le den al botón de "Cerrar sesión"
    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}