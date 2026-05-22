package com.proyecto.PeluPos.data.services.autentication // Asegúrate de que sea tu paquete

import com.proyecto.PeluPos.data.mocks.SessionRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import dagger.Lazy // 👈 ¡Súper importante este import!

class AuthInterceptor @Inject constructor(
    // 👈 Cambiamos a Lazy para romper el bucle infinito de Hilt
    private val sessionRepositoryProvider: Lazy<SessionRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal = chain.request()

        // 1. Extraemos el repositorio real usando .get()
        val sessionRepository = sessionRepositoryProvider.get()

        // 2. Obtenemos el token (Tu lógica original)
        var token = sessionRepository.getUsuarioActual()?.jwtToken

        if (token.isNullOrEmpty()) {
            // Token de prueba hardcodeado
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbCI6IkFETUlOIiwiaWF0IjoxNzE2NDAwMDAwLCJleHAiOjE4OTM0NTYwMDB9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        }

        // Si hay token, lo metemos en la cabecera de la petición
        val requestModificado = if (!token.isNullOrEmpty()) {
            requestOriginal.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            requestOriginal
        }

        return chain.proceed(requestModificado)
    }
}