package com.proyecto.PeluPos.data.mocks.servicio

import com.proyecto.PeluPos.models.Servicio

class ServicioRepository() {
    private val servicioDaoMock: ServicioDaoMock = ServicioDaoMock()
    fun getServicios(): List<Servicio> =
        servicioDaoMock.getAll().toServicios()

    fun getServicio(id: Long): Servicio? =
        servicioDaoMock.get(id)?.toServicio()

    fun insert(servicio: Servicio): Boolean =
        servicioDaoMock.insert(servicio.toServicioMock())

    fun updateServicio(servicio: Servicio): Boolean =
        servicioDaoMock.update(servicio.toServicioMock())

    fun delete(id: Long): Boolean =
        servicioDaoMock.delete(id)
}
