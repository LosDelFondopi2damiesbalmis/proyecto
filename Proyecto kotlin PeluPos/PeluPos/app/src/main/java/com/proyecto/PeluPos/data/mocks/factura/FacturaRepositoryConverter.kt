package com.proyecto.PeluPos.data.mocks.factura

import com.proyecto.PeluPos.data.mocks.FacturaMock
import com.proyecto.PeluPos.data.mocks.cliente.toCliente
import com.proyecto.PeluPos.data.mocks.cliente.toClienteMock
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleado
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleadoMock
import com.proyecto.PeluPos.data.mocks.producto.toProductos
import com.proyecto.PeluPos.data.mocks.producto.toProductosMock
import com.proyecto.PeluPos.data.mocks.servicio.toServicios
import com.proyecto.PeluPos.data.mocks.servicio.toServiciosMock
import com.proyecto.PeluPos.models.Factura
import java.util.Date

fun Factura.toFacturaMock() =
    FacturaMock(
        idFactura,
        monto,
        fecha.toString(),
        pendiente,
        tipoPago,
        cliente.toClienteMock(),
        empleado.toEmpleadoMock(),
        productos.toProductosMock(),
        servicios.toServiciosMock()
    )

fun List<Factura>.toFacturasMock() =
    map { it.toFacturaMock() }

fun FacturaMock.toFactura() =
    Factura(
        idFactura,
        monto,
        Date(),
        pendiente,
        tipoPago,
        cliente.toCliente(),
        empleado.toEmpleado(),
        productos.toProductos().toMutableList(),
        servicios.toServicios().toMutableList()
    )

fun List<FacturaMock>.toFacturas() =
    map { it.toFactura() }
