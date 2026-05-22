package com.proyecto.PeluPos.data.services.autentication

import android.util.Log
import com.proyecto.PeluPos.models.LoginRequest
import com.proyecto.PeluPos.models.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

class ApiServicesException(mensaje: String) : Exception(mensaje)

@Singleton
class AuthServiceImplementation @Inject constructor(
    private val authService: AuthService
) {
    private val logTag = "OkHttp"

    suspend fun login(request: LoginRequest): LoginResponse {
        val mensajeError = "Error al intentar iniciar sesión con ${request.usuario}"

        try {
            val response = authService.login(request)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                val dato = response.body()

                // Si el token es null o vacío, lanzamos error aunque sea un 200 OK
                if (dato?.jwtToken.isNullOrEmpty()) {
                    throw ApiServicesException("Credenciales incorrectas o usuario no encontrado")
                }

                return dato ?: throw ApiServicesException("No hay datos en la respuesta")

            } else {
                val body = response.errorBody()?.string()
                Log.e(logTag, "$mensajeError (código ${response.code()}): \n$body")
                throw ApiServicesException(mensajeError)
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error de red: ${e.localizedMessage}")
            throw ApiServicesException("No se pudo conectar con el servidor. Comprueba tu conexión.")
        }
    }
}