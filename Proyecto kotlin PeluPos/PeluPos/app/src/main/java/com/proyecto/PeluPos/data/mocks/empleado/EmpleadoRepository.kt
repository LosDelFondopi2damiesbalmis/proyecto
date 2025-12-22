package com.proyecto.PeluPos.data.mocks.empleado


import com.proyecto.PeluPos.models.Empleado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmpleadoRepository @Inject constructor() {

    private val _empleados = MutableStateFlow<List<Empleado>>(
        listOf(
            Empleado(
                id = 1,
                nombre = "Ana María",
                apellidos = "García López",
                dni = "12345678A",
                telefono = "600111222",
                email = "ana.garcia@tacuareadas.com",
                especialidades = listOf("Corte", "Coloración", "Mechas"),
                salario = 1850.0,
                tipoContrato = "Fijo",
                activo = true,
                notas = "Especialista en coloración y mechas californianas"
            ),
            Empleado(
                id = 2,
                nombre = "Luis Alberto",
                apellidos = "Martínez Ruiz",
                dni = "87654321B",
                telefono = "600333444",
                email = "luis.martinez@tacuareadas.com",
                especialidades = listOf("Extensiones", "Tratamientos capilares"),
                salario = 1750.0,
                tipoContrato = "Fijo",
                activo = true,
                notas = "Experto en extensiones de pelo natural"
            ),
            Empleado(
                id = 3,
                nombre = "Sofía",
                apellidos = "Rodríguez Fernández",
                dni = "11223344C",
                telefono = "600555666",
                email = "sofia.rodriguez@tacuareadas.com",
                especialidades = listOf("Corte", "Peinados", "Maquillaje"),
                salario = 1600.0,
                tipoContrato = "Temporal",
                activo = false,
                notas = "En periodo de prueba"
            )
        )
    )

    fun getAllEmpleados(): Flow<List<Empleado>> = _empleados

    fun getEmpleadoById(id: Long): Flow<Empleado?> {
        return _empleados.map { lista ->
            lista.find { it.id == id }
        }
    }

    suspend fun searchEmpleados(query: String): Flow<List<Empleado>> {
        return _empleados.map { lista ->
            if (query.isBlank()) {
                lista
            } else {
                lista.filter { empleado ->
                    empleado.nombre.contains(query, ignoreCase = true) ||
                            empleado.apellidos.contains(query, ignoreCase = true) ||
                            empleado.dni.contains(query, ignoreCase = true) ||
                            empleado.email.contains(query, ignoreCase = true) ||
                            empleado.especialidades.any { it.contains(query, ignoreCase = true) } ||
                            empleado.telefono.contains(query, ignoreCase = true)
                }
            }
        }
    }

    suspend fun toggleActivo(id: Long) {
        val nuevaLista = _empleados.value.map { empleado ->
            if (empleado.id == id) {
                empleado.copy(activo = !empleado.activo)
            } else {
                empleado
            }
        }
        _empleados.value = nuevaLista
    }

    suspend fun insertEmpleado(empleado: Empleado) {
        val nuevoId = (_empleados.value.maxOfOrNull { it.id } ?: 0) + 1
        val empleadoConId = empleado.copy(id = nuevoId)
        _empleados.value = _empleados.value + empleadoConId
    }

    suspend fun updateEmpleado(empleado: Empleado) {
        val nuevaLista = _empleados.value.map {
            if (it.id == empleado.id) empleado else it
        }
        _empleados.value = nuevaLista
    }

    suspend fun deleteEmpleado(id: Long) {
        val nuevaLista = _empleados.value.filterNot { it.id == id }
        _empleados.value = nuevaLista
    }

    fun getEmpleadosActivos(): Flow<List<Empleado>> {
        return _empleados.map { lista ->
            lista.filter { it.activo }
        }
    }
}
