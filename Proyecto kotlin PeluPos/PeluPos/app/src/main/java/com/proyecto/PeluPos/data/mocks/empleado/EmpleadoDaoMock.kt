package com.proyecto.PeluPos.data.mocks.empleado

import com.proyecto.PeluPos.data.mocks.EmpleadoMock

class EmpleadoDaoMock {

    private val empleados = mutableListOf(
        EmpleadoMock(1, 600200001, "carlos@mail.com", "Vendedor", "Carlos López"),
        EmpleadoMock(2, 600200002, "elena@mail.com", "Técnico", "Elena Martín"),
        EmpleadoMock(3, 600200003, "pedro@mail.com", "Gerente", "Pedro Santos")
    )

    fun getAll(): List<EmpleadoMock> = empleados.toList()

    fun get(id: Long): EmpleadoMock? =
        empleados.find { it.idEmpleado == id }

    fun insert(e: EmpleadoMock): Boolean {
        (1L..empleados.size + 1L).forEach { i ->
            if (empleados.none { it.idEmpleado == i }) {
                empleados.add(e.copy(idEmpleado = i))
                return true
            }
        }
        return false
    }

    fun update(e: EmpleadoMock): Boolean {
        val index = empleados.indexOfFirst { it.idEmpleado == e.idEmpleado }
        if (index == -1) return false
        empleados[index] = e
        return true
    }

    fun delete(id: Long): Boolean {
        val index = empleados.indexOfFirst { it.idEmpleado == id }
        if (index == -1) return false
        empleados.removeAt(index)
        return true
    }
}
