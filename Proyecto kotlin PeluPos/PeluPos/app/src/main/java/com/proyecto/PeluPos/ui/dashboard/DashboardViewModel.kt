package com.proyecto.PeluPos.ui.dashboard

import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.local.LocalRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val empleadoRepository: EmpleadoRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        val productos = productoRepository.getProductos()
        val empleados = empleadoRepository.getEmpleados()
        val locales = localRepository.getLocales()

        _uiState.update { estadoActual ->
            estadoActual.copy(
                productosBajoStock = productos.filter { it.stock < 5 }, // Filtramos stock bajo real
                totalEmpleados = empleados.size,
                totalLocales = locales.size
            )
        }
    }
}