package com.proyecto.PeluPos.data.mocks.factura

import com.proyecto.PeluPos.models.Factura

class FacturaRepository() {
    private val facturaDaoMock: FacturaDaoMock = FacturaDaoMock()
    fun getFacturas(): List<Factura> =
        facturaDaoMock.getAll().toFacturas()

    fun getFactura(id: Long): Factura? =
        facturaDaoMock.get(id)?.toFactura()

    fun insert(factura: Factura): Boolean =
        facturaDaoMock.insert(factura.toFacturaMock())

    fun updateFactura(factura: Factura): Boolean =
        facturaDaoMock.update(factura.toFacturaMock())

    fun delete(id: Long): Boolean =
        facturaDaoMock.delete(id)
}
