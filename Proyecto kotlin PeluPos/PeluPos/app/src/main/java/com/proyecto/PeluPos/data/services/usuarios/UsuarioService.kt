package com.proyecto.PeluPos.data.services.usuarios
import com.proyecto.PeluPos.models.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {

    // LEER todos los usuarios
    @GET("usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    // CREAR un usuario nuevo
    @POST("usuarios")
    suspend fun crearUsuario(@Body nuevoUsuario: Usuario): Response<Map<String, String>>

    // ACTUALIZAR un usuario (el ID va dentro del objeto 'Usuario')
    @PUT("usuarios")
    suspend fun actualizarUsuario(@Body usuarioModificado: Usuario): Response<Map<String, String>>

    // BORRAR un usuario (pasamos su ID por la URL)
    @DELETE("usuarios/{id}")
    suspend fun borrarUsuario(@Path("id") id: Long): Response<Map<String, String>>
}