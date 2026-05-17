package com.proyecto.PeluPos.data.services.autentication

import com.proyecto.PeluPos.data.mocks.SessionRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionRepository: SessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal = chain.request()

        // Obtenemos el token que guardamos al hacer login
        val token = sessionRepository.getUsuarioActual()?.jwtToken

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