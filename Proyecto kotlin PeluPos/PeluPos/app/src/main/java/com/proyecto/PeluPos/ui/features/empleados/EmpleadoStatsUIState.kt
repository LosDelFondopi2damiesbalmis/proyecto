package com.proyecto.PeluPos.ui.features.empleados

data class EmpleadoStatsUiState(
    val isLoading: Boolean = true,
    val totalFacturado: Double = 0.0,
    val productosVendidos: Int = 0,
    val serviciosRealizados: Int = 0,
    val error: String? = null
)