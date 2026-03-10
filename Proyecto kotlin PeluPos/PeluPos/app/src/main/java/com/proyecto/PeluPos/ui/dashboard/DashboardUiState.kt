package com.proyecto.PeluPos.ui.dashboard

import com.proyecto.PeluPos.models.Producto

// --- 1. ESTADO DE LA UI ---
data class DashboardUiState(
    val nombreUsuarioLogeado: String = "Cargando...",
    val rolUsuarioLogeado: String = "...",

    // Listas reales vacías por defecto
    val productosBajoStock: List<Producto> = emptyList(),
    val totalEmpleados: Int = 0,
    val totalLocales: Int = 0,

    val ultimasVentas: List<Triple<String, String, String>> = emptyList()
)
