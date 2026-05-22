package com.proyecto.PeluPos.data.mocks.usuario

import com.proyecto.PeluPos.data.services.usuarios.UsuarioService
import com.proyecto.PeluPos.models.Usuario
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val api: UsuarioService
) {

    // 1. OBTENER (GET)
    suspend fun getUsuarios(): List<Usuario> {
        val respuesta = api.getUsuarios()

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: emptyList()
        } else {
            throw java.lang.Exception("Error al cargar usuarios: ${respuesta.code()}")
        }
    }

    // 2. CREAR (POST)
    suspend fun crearUsuario(nuevoUsuario: Usuario): String {
        val respuesta = api.crearUsuario(nuevoUsuario)

        if (respuesta.isSuccessful) {
            val mapaRespuesta = respuesta.body()
            return mapaRespuesta?.get("mensaje") ?: "Usuario creado con éxito"
        } else {
            throw java.lang.Exception("Error al crear usuario: ${respuesta.code()}")
        }
    }

    // 3. ACTUALIZAR (PUT)
    suspend fun actualizarUsuario(usuarioModificado: Usuario): String {
        val respuesta = api.actualizarUsuario(usuarioModificado)

        if (respuesta.isSuccessful) {
            val mapaRespuesta = respuesta.body()
            return mapaRespuesta?.get("mensaje") ?: "Usuario actualizado con éxito"
        } else {
            throw java.lang.Exception("Error al actualizar usuario: ${respuesta.code()}")
        }
    }

    // 4. BORRAR (DELETE)
    suspend fun borrarUsuario(id: Long): String {
        val respuesta = api.borrarUsuario(id)

        if (respuesta.isSuccessful) {
            val mapaRespuesta = respuesta.body()
            return mapaRespuesta?.get("mensaje") ?: "Usuario eliminado con éxito"
        } else {
            throw java.lang.Exception("Error al borrar usuario: ${respuesta.code()}")
        }
    }
}
