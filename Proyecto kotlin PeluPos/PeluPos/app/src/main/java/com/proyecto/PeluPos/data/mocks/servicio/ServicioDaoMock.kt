package com.proyecto.PeluPos.data.mocks.servicio

import com.proyecto.PeluPos.data.mocks.ServicioMock

class ServicioDaoMock {

    private val servicios = mutableListOf(
        ServicioMock(1, "Mantenimiento", 50.0, "Revisión general"),
        ServicioMock(2, "Reparación", 80.0, "Cambio de piezas")
    )

    fun getAll(): List<ServicioMock> = servicios.toList()

    fun get(id: Long): ServicioMock? =
        servicios.find { it.idServicio == id }

    fun insert(s: ServicioMock): Boolean {
        (1L..servicios.size + 1L).forEach { i ->
            if (servicios.none { it.idServicio == i }) {
                servicios.add(s.copy(idServicio = i))
                return true
            }
        }
        return false
    }

    fun update(s: ServicioMock): Boolean {
        val index = servicios.indexOfFirst { it.idServicio == s.idServicio }
        if (index == -1) return false
        servicios[index] = s
        return true
    }

    fun delete(id: Long): Boolean {
        val index = servicios.indexOfFirst { it.idServicio == id }
        if (index == -1) return false
        servicios.removeAt(index)
        return true
    }
}
