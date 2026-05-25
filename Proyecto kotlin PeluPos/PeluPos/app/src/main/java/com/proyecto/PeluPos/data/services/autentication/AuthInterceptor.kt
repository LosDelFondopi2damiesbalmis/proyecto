package com.proyecto.PeluPos.data.services.autentication // Asegúrate de que sea tu paquete

import com.proyecto.PeluPos.data.mocks.SessionRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import dagger.Lazy // 👈 ¡Súper importante este import!

class AuthInterceptor @Inject constructor(
    // Mantenemos el Lazy/Provider para evitar el bucle infinito
    private val sessionRepositoryProvider: Lazy<SessionRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOriginal = chain.request()

        // 1. Extraemos el repositorio real
        val sessionRepository = sessionRepositoryProvider.get()

        // 2. Obtenemos el token REAL de la sesión
        val token = sessionRepository.getUsuarioActual()?.jwtToken

        // 3. Si hay un token guardado, lo inyectamos. Si no, mandamos la petición limpia.
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