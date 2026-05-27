package com.proyecto.PeluPos.data.services.facturas

import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.FacturaDto
import com.proyecto.PeluPos.models.FacturaProductoDto
import com.proyecto.PeluPos.models.FacturaRequestDto
import com.proyecto.PeluPos.models.FacturaServicioDto
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


interface FacturaProductoService {

    @GET("facturaproductos")
    suspend fun getFacturaProductos(): Response<List<FacturaProductoDto>>

    // ⚠️ Fíjate cómo pasamos los dos IDs en la ruta
    @GET("facturaproductos/{idFactura}/{idProducto}")
    suspend fun getFacturaProducto(
        @Path("idFactura") idFactura: Long,
        @Path("idProducto") idProducto: Long
    ): Response<FacturaProductoDto>

    @POST("facturaproductos")
    suspend fun createFacturaProducto(@Body facturaProducto: FacturaProductoDto): Response<Map<String, String>>
    @DELETE("facturaproductos/{idFactura}/{idProducto}")
    suspend fun deleteFacturaProducto(
        @Path("idFactura") idFactura: Long,
        @Path("idProducto") idProducto: Long
    ): Response<Unit>
}
interface FacturaServicioService {

    @GET("facturaservicios")
    suspend fun getFacturaServicios(): Response<List<FacturaServicioDto>>

    // ⚠️ Igual aquí, necesitamos ambos IDs
    @GET("facturaservicios/{idFactura}/{idServicio}")
    suspend fun getFacturaServicio(
        @Path("idFactura") idFactura: Long,
        @Path("idServicio") idServicio: Long
    ): Response<FacturaServicioDto>

    @POST("facturaservicios")
    suspend fun createFacturaServicio(@Body facturaServicio: FacturaServicioDto): Response<Map<String, String>>
    @DELETE("facturaservicios/{idFactura}/{idServicio}")
    suspend fun deleteFacturaServicio(
        @Path("idFactura") idFactura: Long,
        @Path("idServicio") idServicio: Long
    ): Response<Unit>
}