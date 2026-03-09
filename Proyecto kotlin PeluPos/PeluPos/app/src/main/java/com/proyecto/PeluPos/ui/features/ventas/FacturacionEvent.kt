package com.proyecto.PeluPos.ui.features.ventas

import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio

sealed interface FacturacionEvent {
    // Crear Factura
    data class OnEmpleadoSeleccionado(val empleado: Empleado) : FacturacionEvent
    data class OnClienteSeleccionado(val cliente: Cliente) : FacturacionEvent
    data class OnTipoPagoSeleccionado(val tipoPago: String) : FacturacionEvent
    object OnGuardarFactura : FacturacionEvent

    // Historial
    data class OnSearchQueryChange(val query: String) : FacturacionEvent
}