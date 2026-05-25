package com.proyecto.PeluPos.data.mocks.servicio

import com.proyecto.PeluPos.data.services.servicios.ServicioService
import com.proyecto.PeluPos.models.Servicio
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ServicioRepository @Inject constructor(
    private val servicioService: ServicioService // 🚀 Inyectamos Retrofit
) {

    suspend fun getServicios(): List<Servicio> {
        val response = servicioService.getServicios()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error del servidor al obtener servicios: ${response.code()}")
        }
    }

    suspend fun createServicio(servicio: Servicio): Servicio {
        val response = servicioService.createServicio(servicio)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo crear el servicio en el servidor")
        }
    }

    suspend fun updateServicio(servicio: Servicio): Servicio {
        val response = servicioService.updateServicio(servicio)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo actualizar el servicio")
        }
    }

    suspend fun deleteServicio(id: Long) {
        val response = servicioService.deleteServicio(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo borrar el servicio")
        }
    }
}
