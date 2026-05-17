package com.proyecto.PeluPos.data.services.autentication

import com.proyecto.PeluPos.models.LoginRequest
import com.proyecto.PeluPos.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @POST("login") // ¡Ojo! Asegúrate de que esta ruta coincida con el @Path de tu Java
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}