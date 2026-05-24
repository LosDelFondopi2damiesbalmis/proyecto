package com.proyecto.PeluPos.data.services.autentication

import com.proyecto.PeluPos.models.LoginRequest
import com.proyecto.PeluPos.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // 🚀 NUEVO: Endpoint para cerrar sesión
    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
    // Usamos Unit porque nos da igual el JSON de respuesta, solo queremos que llegue el OK
}
