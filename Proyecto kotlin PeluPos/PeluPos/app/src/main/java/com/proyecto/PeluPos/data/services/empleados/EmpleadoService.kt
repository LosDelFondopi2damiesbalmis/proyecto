package com.proyecto.PeluPos.data.services.empleados

import com.proyecto.PeluPos.models.Empleado
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EmpleadoService {

    // 1. Obtener todos los empleados
    @GET("empleados")
    suspend fun getEmpleados(): Response<List<Empleado>>

    // 2. Obtener un empleado por su ID
    @GET("empleados/{id}")
    suspend fun getEmpleado(
        @Path("id") id: Long
    ): Response<Empleado>

    // 3. Crear un nuevo empleado (se manda el objeto en el Body)
    @POST("empleados")
    suspend fun createEmpleado(
        @Body empleado: Empleado
    ): Response<Empleado>

    // 4. Actualizar un empleado (necesitamos el ID en la URL y el objeto en el Body)
    @PUT("empleados/{id}")
    suspend fun updateEmpleado(
        @Path("id") id: Long,
        @Body empleado: Empleado
    ): Response<Empleado>

    // 5. Borrar un empleado (solo necesitamos mandarle el ID)
    @DELETE("empleados/{id}")
    suspend fun deleteEmpleado(
        @Path("id") id: Long
    ): Response<Unit>
}