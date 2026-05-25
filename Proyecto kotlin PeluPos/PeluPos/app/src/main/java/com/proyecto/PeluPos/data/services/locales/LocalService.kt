package com.proyecto.PeluPos.data.services.locales

import com.proyecto.PeluPos.models.Local
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LocalService {

    @GET("locales")
    suspend fun getLocales(): Response<List<Local>>

    @GET("locales/{id}")
    suspend fun getLocal(@Path("id") id: Long): Response<Local>

    @POST("locales")
    suspend fun createLocal(@Body local: Local): Response<Local>

    @PUT("locales")
    suspend fun updateLocal(@Body local: Local): Response<Local>

    @DELETE("locales/{id}")
    suspend fun deleteLocal(@Path("id") id: Long): Response<Unit>
}