package com.proyecto.PeluPos.data.mocks.factura

import com.proyecto.PeluPos.data.mocks.ClienteMock
import com.proyecto.PeluPos.data.mocks.EmpleadoMock
import com.proyecto.PeluPos.data.mocks.FacturaMock
import com.proyecto.PeluPos.data.mocks.ProductoMock
import com.proyecto.PeluPos.data.mocks.ServicioMock

class FacturaDaoMock {

    private val facturas = mutableListOf(
        FacturaMock(
            idFactura = 1,
            monto = 120.0,
            fecha = "2024-01-01",
            pendiente = false,
            tipoPago = "Efectivo",
            cliente = ClienteMock(1, "Juan Pérez", 150.0, 600111111),
            empleado = EmpleadoMock(1, 600200001, "carlos@mail.com", "Vendedor", "Carlos López"),
            productos = listOf(ProductoMock(1, "Laptop X", 500.0, 750.0, 10)),
            servicios = listOf(ServicioMock(1, "Mantenimiento", 50.0, "Revisión general"))
        )
    )

    fun getAll(): List<FacturaMock> = facturas.toList()

    fun get(id: Long): FacturaMock? =
        facturas.find { it.idFactura == id }

    fun insert(f: FacturaMock): Boolean {
        (1L..facturas.size + 1L).forEach { i ->
            if (facturas.none { it.idFactura == i }) {
                facturas.add(f.copy(idFactura = i))
                return true
            }
        }
        return false
    }

    fun update(f: FacturaMock): Boolean {
        val index = facturas.indexOfFirst { it.idFactura == f.idFactura }
        if (index == -1) return false
        facturas[index] = f
        return true
    }

    fun delete(id: Long): Boolean {
        val index = facturas.indexOfFirst { it.idFactura == id }
        if (index == -1) return false
        facturas.removeAt(index)
        return true
    }
}
