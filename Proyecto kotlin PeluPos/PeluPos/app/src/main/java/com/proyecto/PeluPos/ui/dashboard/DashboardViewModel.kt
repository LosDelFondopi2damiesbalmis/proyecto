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
    private val sessionRepository: SessionRepository,
    private val empleadoRepository: EmpleadoRepository,
    private val localRepository: LocalRepository,
    private val productoRepository: ProductoRepository,
    private val facturaRepository: FacturaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        viewModelScope.launch {
            try {
                // ==========================================
                // 1. CARGAMOS EL USUARIO PRIMERO
                // ==========================================
                val usuarioActual = sessionRepository.getUsuarioActual()

                if (usuarioActual == null || usuarioActual.jwtToken.isNullOrEmpty()) {
                    println("⚠️ Dashboard abortado: No hay usuario o token válido.")
                    // 🚀 Le decimos a la UI que deje de cargar y muestre un error
                    _uiState.update {
                        it.copy(
                            nombreUsuarioLogeado = "Sesión no iniciada",
                            rolUsuarioLogeado = "Error"
                        )
                    }
                    return@launch
                }

                val nombre = usuarioActual.usuario?: ""
                val rol = usuarioActual.rolUsuario?: ""

                // 🚀 ACTUALIZAMOS LA UI INMEDIATAMENTE CON EL USUARIO
                _uiState.update {
                    it.copy(
                        nombreUsuarioLogeado = nombre,
                        rolUsuarioLogeado = rol
                    )
                }

                // ==========================================
                // 2. CARGAMOS EL RESTO DE DATOS PESADOS
                // ==========================================
                val empleados = empleadoRepository.getEmpleados().size
                val locales = localRepository.getLocales().size

                val bajoStock = productoRepository.obtenerProductos()
                    .filter { it.stock < 5 }
                    .take(4)

                val facturasReales = facturaRepository.getFacturas()
                    .sortedByDescending { it.idFactura }
                    .take(4)

                val ventasFormateadas = facturasReales.map { factura ->
                    Triple(
                        "Hoy",
                        factura.cliente?.nombre ?: "Anónimo",
                        "${factura.monto} €"
                    )
                }

                // 🚀 ACTUALIZAMOS LA UI CON LOS CONTADORES
                _uiState.update {
                    it.copy(
                        totalEmpleados = empleados,
                        totalLocales = locales,
                        productosBajoStock = bajoStock,
                        ultimasVentas = ventasFormateadas
                    )
                }

            } catch (e: Exception) {
                println("🚨 ERROR EN EL DASHBOARD: ${e.message}")
                e.printStackTrace()

                // Si por algún motivo nos da 401 estando logueados, limpiamos la UI
                _uiState.update {
                    it.copy(
                        nombreUsuarioLogeado = if (it.nombreUsuarioLogeado.contains("Cargando", ignoreCase = true)) "Desconocido" else it.nombreUsuarioLogeado,
                        rolUsuarioLogeado = if (it.rolUsuarioLogeado.contains("Cargando", ignoreCase = true)) "Error" else it.rolUsuarioLogeado
                    )
                }
            }
        }
    }
    // Dentro de DashboardViewModel.kt
    fun cerrarSesion() {
        viewModelScope.launch {
            // A) Borramos del disco
            sessionRepository.cerrarSesion()

            // B) 🚀 RESETEAMOS EL ESTADO A CERO
            // Esto es clave: al crear un objeto DashboardUiState() vacío,
            // el Sidebar recibe un estado con los valores por defecto (ej: "Cargando...")
            _uiState.value = DashboardUiState()
        }
    }
    }