package com.proyecto.PeluPos.data.services.servicios

import com.proyecto.PeluPos.models.Servicio
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServicioService {

    @GET("servicios")
    suspend fun getServicios(): Response<List<Servicio>>

    @POST("servicios")
    suspend fun createServicio(@Body servicio: Servicio): Response<Servicio>

    @PUT("servicios")
    suspend fun updateServicio(@Body servicio: Servicio): Response<Servicio>

    @DELETE("servicios/{id}")
    suspend fun deleteServicio(@Path("id") id: Long): Response<Unit>
}