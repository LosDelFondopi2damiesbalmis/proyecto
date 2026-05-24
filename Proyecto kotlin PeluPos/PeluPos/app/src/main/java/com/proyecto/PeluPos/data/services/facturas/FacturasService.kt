package com.proyecto.PeluPos.data.services.facturas

import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.FacturaDto
import com.proyecto.PeluPos.models.FacturaRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FacturaService {

    @GET("facturas")
    suspend fun getFacturas(): Response<List<FacturaDto>>

    @GET("facturas/{idFactura}")
    suspend fun getFactura(@Path("idFactura") id: Long): Response<FacturaDto>

    @POST("facturas")
    suspend fun createFactura(@Body factura: FacturaRequestDto): Response<Factura>

    @PUT("facturas/{idFactura}")
    suspend fun updateFactura(@Path("idFactura") id: Long, @Body factura: FacturaRequestDto): Response<Factura>

    @DELETE("facturas/{idFactura}")
    suspend fun deleteFactura(@Path("idFactura") id: Long): Response<Unit>
}