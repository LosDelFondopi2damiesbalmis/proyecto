package com.proyecto.PeluPos.ui.features.empleados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.factura.FacturaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EmpleadoStatsViewModel @Inject constructor(
    // 🚨 Inyecta aquí tu repositorio donde sacas el historial de ventas/facturas
    private val facturaRepository: FacturaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmpleadoStatsUiState())
    val uiState: StateFlow<EmpleadoStatsUiState> = _uiState.asStateFlow()

    fun cargarEstadisticasDesdeKotlin(idEmpleado: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // 1. Bajamos todas las facturas de la base de datos
                val todasLasFacturas = facturaRepository.getFacturas()

                // 2. Averiguamos cuál es el mes actual
                val mesActual = YearMonth.now()

                // 3. ¡LA MAGIA DE KOTLIN! Filtramos las facturas del empleado de este mes
                val facturasDelMes = todasLasFacturas.filter { factura ->
                    // 1. ¿Es de este empleado?
                    val esDelEmpleado = factura.empleado.idEmpleado == idEmpleado

                    // 2. ¿Es de este mes? (Convertimos el Date de Java a YearMonth de Kotlin)
                    val fechaFactura = factura.fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    val esDeEsteMes = YearMonth.from(fechaFactura) == mesActual

                    esDelEmpleado && esDeEsteMes
                }

                // 4. Sumamos todo (Ajusta los nombres a tu modelo de Factura)
                val totalDinero = facturasDelMes.sumOf { it.monto } // o it.total
                val totalProductos = facturasDelMes.sumOf { it.productos.size }
                val totalServicios = facturasDelMes.sumOf { it.servicios.size }

                // 5. Lo subimos a la pantalla
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        totalFacturado = totalDinero,
                        productosVendidos = totalProductos,
                        serviciosRealizados = totalServicios
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al calcular: ${e.message}") }
            }
        }
    }
}