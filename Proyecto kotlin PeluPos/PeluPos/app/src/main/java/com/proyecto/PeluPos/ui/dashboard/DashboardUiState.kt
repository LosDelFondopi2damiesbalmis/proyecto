package com.proyecto.PeluPos.ui.dashboard

import com.proyecto.PeluPos.models.Producto

// --- 1. ESTADO DE LA UI ---
data class DashboardUiState(
    val nombreUsuarioLogeado: String = "Admin",
    val rolUsuarioLogeado: String = "Peluquería Central",

    // Listas para las tarjetas
    val productosBajoStock: List<Producto> = emptyList(),
    val totalEmpleados: Int = 0,
    val totalLocales: Int = 0,

    // Datos simulados (hasta que tengas repositorio de ventas)
    val ultimasVentas: List<Triple<String, String, String>> = listOf(
        Triple("10:15", "Ana López", "45,00 €"),
        Triple("10:45", "María Pérez", "35,00 €"),
        Triple("11:05", "Juan Ruiz", "18,00 €")
    )
)

