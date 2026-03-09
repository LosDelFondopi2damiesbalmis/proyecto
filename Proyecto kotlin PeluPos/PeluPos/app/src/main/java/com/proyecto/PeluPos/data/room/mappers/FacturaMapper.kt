package com.proyecto.PeluPos.data.mappers

import com.proyecto.PeluPos.data.room.entity.FacturaEntity
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import java.util.Date

// De modelo a entity
fun Factura.toEntity() = FacturaEntity(
    idFactura = idFactura,
    clienteId = cliente.idCliente,
    empleadoId = empleado.idEmpleado,
    monto = monto,
    fecha = fecha.time,
    pendiente = pendiente,
    tipoPago = tipoPago
)

// De entity a modelo
fun FacturaEntity.toModel(
    cliente: Cliente,
    empleado: Empleado,
    productos: MutableList<com.proyecto.PeluPos.models.Producto> = mutableListOf(),
    servicios: MutableList<com.proyecto.PeluPos.models.Servicio> = mutableListOf()
) = Factura(
    idFactura = idFactura,
    monto = monto,
    fecha = Date(fecha),
    pendiente = pendiente,
    tipoPago = tipoPago,
    cliente = cliente,
    empleado = empleado,
    productos = productos,
    servicios = servicios
)