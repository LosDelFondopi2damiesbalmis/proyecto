package com.proyecto.PeluPos.ui.features.servicios
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import com.proyecto.PeluPos.models.Servicio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ServiciosViewModel @Inject constructor(
    private val servicioRepository: ServicioRepository,
    private val empleadoRepository: EmpleadoRepository // Necesario para el desplegable
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiciosUiState())
    val uiState: StateFlow<ServiciosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        // 🚀 Abrimos la corrutina para ir a internet en segundo plano
        viewModelScope.launch {
            try {
                // Pedimos los datos reales a los repositorios (las líneas rojas desaparecerán)
                val servicios = servicioRepository.getServicios()
                val empleados = empleadoRepository.getEmpleados()

                // Actualizamos la interfaz cuando los datos ya han llegado
                _uiState.update {
                    it.copy(
                        todosLosServicios = servicios,
                        serviciosVisibles = filtrarServicios(servicios, it.searchQuery),
                        empleadosDisponibles = empleados
                    )
                }
            } catch (e: Exception) {
                // Si Tomcat falla o no hay conexión, evitamos que la app explote
                println("🚨 Error al cargar servicios o empleados: ${e.message}")
            }
        }
    }

    private fun filtrarServicios(lista: List<Servicio>, query: String): List<Servicio> {
        if (query.isBlank()) return lista
        return lista.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.empleado.nombre.contains(query, ignoreCase = true)
        }
    }

    fun onEvent(event: ServiciosEvent) {
        when (event) {
            ServiciosEvent.CargarDatos -> cargarDatos()
            is ServiciosEvent.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        serviciosVisibles = filtrarServicios(it.todosLosServicios, event.query)
                    )
                }
            }

            ServiciosEvent.PrepararNuevoServicio -> {
                _uiState.update {
                    it.copy(editandoServicioId = null, formNombre = "", formPrecio = "", formDescripcion = "", formEmpleadoSeleccionado = null)
                }
            }

            is ServiciosEvent.PrepararEdicion -> {
                val servicio = _uiState.value.todosLosServicios.find { it.idServicio == event.idServicio }
                servicio?.let { srv ->
                    _uiState.update {
                        it.copy(
                            editandoServicioId = srv.idServicio,
                            formNombre = srv.nombre,
                            formPrecio = srv.precio.toString(),
                            formDescripcion = srv.descripcion,
                            formEmpleadoSeleccionado = srv.empleado
                        )
                    }
                }
            }

            is ServiciosEvent.OnNombreChange -> _uiState.update { it.copy(formNombre = event.nombre) }
            is ServiciosEvent.OnPrecioChange -> _uiState.update { it.copy(formPrecio = event.precio) }
            is ServiciosEvent.OnDescripcionChange -> _uiState.update { it.copy(formDescripcion = event.descripcion) }
            is ServiciosEvent.OnEmpleadoChange -> _uiState.update { it.copy(formEmpleadoSeleccionado = event.empleado) }

            ServiciosEvent.GuardarServicio -> guardarServicio()
            ServiciosEvent.BorrarServicio -> borrarServicio()
        }
    }

    private fun guardarServicio() {
        val state = _uiState.value
        val empleado = state.formEmpleadoSeleccionado ?: return

        val nuevoServicio = Servicio(
            idServicio = state.editandoServicioId ?: System.currentTimeMillis(),
            nombre = state.formNombre,
            precio = state.formPrecio.replace(",", ".").toDoubleOrNull() ?: 0.0,
            descripcion = state.formDescripcion,
            empleado = empleado
        )

        if (state.editandoServicioId != null) {
            servicioRepository.updateServicio(nuevoServicio)
        } else {
            servicioRepository.insert(nuevoServicio)
        }
        cargarDatos()
    }

    private fun borrarServicio() {
        _uiState.value.editandoServicioId?.let { id ->
            servicioRepository.delete(id)
            cargarDatos()
        }
    }
}