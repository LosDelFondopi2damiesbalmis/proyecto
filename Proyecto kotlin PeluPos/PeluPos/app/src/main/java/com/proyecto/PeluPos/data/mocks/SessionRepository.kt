package com.proyecto.PeluPos.data.mocks

import com.proyecto.PeluPos.data.services.autentication.AuthServiceImplementation
import com.proyecto.PeluPos.models.LoginRequest
import com.proyecto.PeluPos.models.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton // <-- ¡Súper importante este import!

@Singleton // 1. ¡OJO! Añadimos @Singleton aquí para que la sesión sea ÚNICA en toda la app
class SessionRepository @Inject constructor(
    private val authService: AuthServiceImplementation
) {
    // 2. Esta variable guardará los datos de la sesión en la memoria del móvil
    private var usuarioActual: LoginResponse? = null

    suspend fun login(usuario: String, contrasena: String): LoginResponse = withContext(Dispatchers.IO) {
        val request = LoginRequest(usuario = usuario, contrasena = contrasena)

        // Hacemos la llamada a la API
        val response = authService.login(request)

        // 3. Si el login es correcto y nos llega el token, lo guardamos en la memoria
        if (!response.jwtToken.isNullOrEmpty()) {
            usuarioActual = response
        }

        response
    }

    // 4. EL MÉTODO QUE TE FALTABA: Devuelve el usuario logueado actualmente
    fun getUsuarioActual(): LoginResponse? {
        return usuarioActual
    }

    // 5. Un extra que te vendrá genial para cuando pongas el botón de "Cerrar Sesión"
    fun cerrarSesion() {
        usuarioActual = null
    }
}