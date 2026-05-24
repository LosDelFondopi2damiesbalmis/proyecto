package com.proyecto.PeluPos.data.mocks.local

import com.proyecto.PeluPos.data.services.locales.LocalService
import com.proyecto.PeluPos.models.Local
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val localService: LocalService // 🚀 Adiós Mock, hola Retrofit
) {

    // 1. Obtener la lista de todos los locales
    suspend fun getLocales(): List<Local> {
        val response = localService.getLocales()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error del servidor al obtener locales: ${response.code()}")
        }
    }

    // 2. Obtener un local por su ID
    suspend fun getLocal(id: Long): Local? {
        val response = localService.getLocal(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error al buscar el local: ${response.code()}")
        }
    }

    // 3. Crear un nuevo local (Sustituye a tu antiguo 'insert')
    suspend fun createLocal(local: Local): Local {
        val response = localService.createLocal(local)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo crear el local en el servidor")
        }
    }

    // 4. Actualizar un local existente
    suspend fun updateLocal(id: Long, local: Local): Local {
        val response = localService.updateLocal(id, local)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo actualizar el local")
        }
    }

    // 5. Borrar un local (Sustituye a tu antiguo 'delete')
    suspend fun deleteLocal(id: Long) {
        val response = localService.deleteLocal(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo borrar el local")
        }
    }
}
