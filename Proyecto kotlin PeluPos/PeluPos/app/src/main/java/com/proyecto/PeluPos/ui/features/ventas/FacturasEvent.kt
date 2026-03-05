package com.proyecto.PeluPos.ui.features.ventas

import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado

sealed interface CrearFacturaEvent {
    data class OnEmpleadoSeleccionado(val empleado: Empleado) : CrearFacturaEvent
    data class OnClienteSeleccionado(val cliente: Cliente) : CrearFacturaEvent
    data class OnTipoPagoSeleccionado(val tipoPago: String) : CrearFacturaEvent
    object OnGuardarFactura : CrearFacturaEvent
}