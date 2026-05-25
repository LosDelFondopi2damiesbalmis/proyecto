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
import com.proyecto.PeluPos.models.ClienteIdDto
import com.proyecto.PeluPos.models.EmpleadoIdDto
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.FacturaDto
import com.proyecto.PeluPos.models.FacturaRequestDto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
fun FacturaDto.toDomain(): Factura {
    return Factura(
        idFactura = this.idFactura,
        monto = this.monto,
        // Aquí conviertes el string "2026-03-05T10:30:00" a Date si lo necesitas,
        // o lo mantienes como String si prefieres manejarlo así en la UI
        fecha = Date(),
        pendiente = this.pendiente,
        tipoPago = this.tipoPago,
        cliente = this.idCliente,
        empleado = this.idEmpleado,
        productos = this.facturaProductoCollection.map { it.producto }.toMutableList(),
        servicios = this.facturaServicioCollection.map { it.servicio }.toMutableList()
    )
}
fun Factura.toRequestDto(): FacturaRequestDto {
    return FacturaRequestDto(
        idFactura = if (this.idFactura == 0L) null else this.idFactura,
        monto = this.monto,
        // Formato que espera el servidor: yyyy-MM-dd'T'HH:mm:ss
        fecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(this.fecha),
        pendiente = this.pendiente,
        tipoPago = this.tipoPago,
        idCliente = ClienteIdDto(this.cliente.idCliente),
        idEmpleado = EmpleadoIdDto(this.empleado.idEmpleado)
    )
}