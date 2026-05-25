package com.proyecto.PeluPos.data.services.clientes

import com.proyecto.PeluPos.models.Cliente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteService {

    @GET("clientes")
    suspend fun getClientes(): Response<List<Cliente>>

    @GET("clientes/{id}")
    suspend fun getCliente(@Path("id") id: Long): Response<Cliente>

    @POST("clientes")
    suspend fun createCliente(@Body cliente: Cliente): Response<Cliente>

    @PUT("clientes/{id}")
    suspend fun updateCliente(@Path("id") id: Long, @Body cliente: Cliente): Response<Cliente>

    @DELETE("clientes/{id}")
    suspend fun deleteCliente(@Path("id") id: Long): Response<Unit>
}