package com.proyecto.PeluPos.data.mocks.empleado

import com.proyecto.PeluPos.data.services.empleados.EmpleadoService
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.ResumenVentas
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmpleadoRepository @Inject constructor(
    private val empleadoService: EmpleadoService
) {

    // 1. Obtener la lista completa de empleados
    suspend fun getEmpleados(): List<Empleado> {
        val response = empleadoService.getEmpleados()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error de servidor al obtener empleados: ${response.code()}")
        }
    }

    // 2. Obtener un empleado por ID
    suspend fun getEmpleadoById(id: Long): Empleado? {
        val response = empleadoService.getEmpleado(id)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error al buscar el empleado: ${response.code()}")
        }
    }

    // 3. Crear empleado
    suspend fun createEmpleado(empleado: Empleado): Empleado {
        val response = empleadoService.createEmpleado(empleado)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo crear el empleado en el servidor")
        }
    }

    // 4. Actualizar empleado
    suspend fun updateEmpleado(empleado: Empleado): Empleado {
        val response = empleadoService.updateEmpleado(empleado)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception("No se pudo actualizar el empleado")
        }
    }

    // 5. Borrar empleado
    suspend fun deleteEmpleado(id: Long) {
        val response = empleadoService.deleteEmpleado(id)
        if (!response.isSuccessful) {
            throw Exception("No se pudo borrar el empleado")
        }
    }
    // En tu EmpleadoRepository:
    suspend fun getResumenVentas(idEmpleado: Long): ResumenVentas {
        return empleadoService.getResumenVentasEmpleado(idEmpleado)
    }
}
