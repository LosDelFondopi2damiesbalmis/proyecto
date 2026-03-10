package com.proyecto.PeluPos.ui.dashboard


import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.local.LocalRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.SessionRepository
import com.proyecto.PeluPos.data.mocks.factura.FacturaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionRepository: SessionRepository, // Para el usuario logeado
    private val empleadoRepository: EmpleadoRepository, // Para contar empleados
    private val localRepository: LocalRepository,       // Para contar locales
    private val productoRepository: ProductoRepository, // Para el stock
    private val facturaRepository: FacturaRepository    // Para las últimas ventas (ajusta el nombre si usas otro)
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            try {
                // 1. Cargamos el usuario
                val usuarioActual = sessionRepository.getUsuarioActual()
                val nombre = usuarioActual?.empleado?.nombre ?: "Admin"
                val rol = usuarioActual?.rolUsuario?.name ?: "Sin Rol"

                // 2. Cargamos contadores
                val empleados = empleadoRepository.getEmpleados().size
                val locales = localRepository.getLocales().size

                // 3. Cargamos productos
                val bajoStock = productoRepository.getProductos()
                    .filter { it.stock < 5 }
                    .take(4)

                // 4. Cargamos ventas
                val facturasReales = facturaRepository.getFacturas()
                    .sortedByDescending { it.idFactura }
                    .take(4)

                val ventasFormateadas = facturasReales.map { factura ->
                    Triple(
                        "Hoy",
                        factura.cliente?.nombre ?: "Anónimo", // <-- Cuidado aquí si cliente es null
                        "${factura.monto} €"
                    )
                }

                // 5. Actualizamos el estado
                _uiState.update {
                    it.copy(
                        nombreUsuarioLogeado = nombre,
                        rolUsuarioLogeado = rol,
                        totalEmpleados = empleados,
                        totalLocales = locales,
                        productosBajoStock = bajoStock,
                        ultimasVentas = ventasFormateadas
                    )
                }
            } catch (e: Exception) {
                // Si algo falla, lo imprimimos en el Logcat (la consola de Android Studio)
                println("🚨 ERROR EN EL DASHBOARD: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}