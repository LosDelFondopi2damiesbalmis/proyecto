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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val servicios = servicioRepository.getServicios()
                val empleados = empleadoRepository.getEmpleados()

                _uiState.update {
                    it.copy(
                        todosLosServicios = servicios,
                        serviciosVisibles = filtrarServicios(servicios, it.searchQuery),
                        empleadosDisponibles = empleados,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar servicios o empleados: ${e.message}"
                    )
                }
            }
        }
    }

    private fun filtrarServicios(lista: List<Servicio>, query: String): List<Servicio> {
        if (query.isBlank()) return lista
        return lista.filter {
            (it.nombre?.contains(query, ignoreCase = true) ?: false) ||
                    (it.empleado?.nombre?.contains(query, ignoreCase = true) ?: false)
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
                    it.copy(
                        editandoServicioId = null,
                        formNombre = "",
                        formPrecio = "",
                        formDescripcion = "",
                        formEmpleadoSeleccionado = null,
                        mensaje = null,
                        error = null
                    )
                }
            }

            is ServiciosEvent.PrepararEdicion -> {
                val servicio = _uiState.value.todosLosServicios.find { it.idServicio == event.idServicio }
                servicio?.let { srv ->
                    _uiState.update {
                        it.copy(
                            editandoServicioId = srv.idServicio,
                            formNombre = srv.nombre ?: "",
                            formPrecio = srv.precio?.toString() ?: "",
                            formDescripcion = srv.descripcion ?: "",
                            formEmpleadoSeleccionado = srv.empleado,
                            mensaje = null,
                            error = null
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

        // Mapeamos el objeto Servicio listo para enviar a la API
        val servicioDatos = Servicio(
            // ⚠️ Importante: Mandamos 0L en nuevos para que la BD aplique AUTO_INCREMENT
            idServicio = state.editandoServicioId ?: 0L,
            nombre = state.formNombre,
            precio = state.formPrecio.replace(",", ".").toDoubleOrNull() ?: 0.0,
            descripcion = state.formDescripcion,
            empleado = empleado
        )

        // 🚀 Usamos viewModelScope.launch porque llamamos a la API de Retrofit
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoServicioId != null) {
                    // 🔄 ACTUALIZAR (PUT): Enviamos el ID y los datos modificados
                    servicioRepository.updateServicio(state.editandoServicioId, servicioDatos)
                    _uiState.update { it.copy(mensaje = "Servicio actualizado con éxito") }
                } else {
                    // ➕ CREAR (POST): Enviamos el nuevo servicio a Tomcat
                    servicioRepository.createServicio(servicioDatos)
                    _uiState.update { it.copy(mensaje = "Servicio guardado con éxito") }
                }

                cargarDatos() // Sincronizamos la app trayendo la lista actualizada del servidor
                onEvent(ServiciosEvent.PrepararNuevoServicio) // Limpiamos el formulario

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al guardar el servicio: ${e.message}")
                }
            }
        }
    }

    private fun borrarServicio() {
        val id = _uiState.value.editandoServicioId
        if (id != null) {
            // 🚀 Usamos viewModelScope.launch para el borrado asíncrono
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                try {
                    // ❌ ELIMINAR (DELETE): Pasamos el ID a Retrofit
                    servicioRepository.deleteServicio(id)
                    _uiState.update { it.copy(mensaje = "Servicio eliminado con éxito") }

                    cargarDatos() // Actualizamos lista completa
                    onEvent(ServiciosEvent.PrepararNuevoServicio) // Reseteamos formulario
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Error al eliminar el servicio: ${e.message}")
                    }
                }
            }
        }
    }
}