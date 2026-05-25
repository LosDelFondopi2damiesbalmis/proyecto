package com.proyecto.PeluPos.data.services.productos
import com.proyecto.PeluPos.models.Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoService {

    // 1. GET: Leer todos
    @GET("productos")
    suspend fun getProductos(): Response<List<Producto>>

    // 2. POST: Crear un producto nuevo
    // Usamos @Body para enviar el objeto entero. Retrofit lo pasará a JSON automáticamente.
    @POST("productos")
    suspend fun crearProducto(@Body nuevoProducto: Producto): Response<Producto>
    // (A veces el backend no devuelve el producto, en ese caso pon Response<Void>)

    // 3. PUT: Actualizar un producto existente
    @PUT("productos")
    suspend fun actualizarProducto(@Body productoActualizado: Producto): Response<Producto>

    // 4. DELETE: Borrar un producto
    // Solo necesitamos pasarle el ID en la URL
    @DELETE("productos/{idProducto}")
    suspend fun borrarProducto(@Path("idProducto") id: Int): Response<Void>
}