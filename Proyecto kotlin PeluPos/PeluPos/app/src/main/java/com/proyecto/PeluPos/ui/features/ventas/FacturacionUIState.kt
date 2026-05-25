package com.proyecto.PeluPos.ui.features.ventas

import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio

data class FacturacionUiState(
    // Datos TPV
    val carritoProductos: List<Producto> = emptyList(),
    val carritoServicios: List<Servicio> = emptyList(),

    // Datos Formulario Factura
    val productosDisponibles: List<Producto> = emptyList(),
    val serviciosDisponibles: List<Servicio> = emptyList(),
    val empleadosDisponibles: List<Empleado> = emptyList(),
    val clientesDisponibles: List<Cliente> = emptyList(),
    val empleadoSeleccionado: Empleado? = null,
    val clienteSeleccionado: Cliente? = null,
    val tipoPago: String = "Efectivo",

    // Datos Historial
    val todasLasFacturas: List<Factura> = emptyList(), // Cache
    val facturasVisibles: List<Factura> = emptyList(), // Las que se muestran tras filtrar
    val searchQuery: String = "",

    // 🚀 NUEVO: Banderas de control de red para Retrofit
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
)