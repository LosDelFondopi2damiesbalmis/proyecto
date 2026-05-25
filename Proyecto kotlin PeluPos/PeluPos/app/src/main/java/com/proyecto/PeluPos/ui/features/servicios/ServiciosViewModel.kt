package com.proyecto.PeluPos.ui.features.servicios
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import com.proyecto.PeluPos.models.Servicio
import com.proyecto.PeluPos.navigation.ServicioFormRoute
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
    private val empleadoRepository: EmpleadoRepository,
    private val savedStateHandle: SavedStateHandle // 🚀 1. INYECTAMOS LA ANTENA
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
                // Pedimos los datos reales asíncronamente a Tomcat
                val serviciosReales = servicioRepository.getServicios()
                val empleadosReales = empleadoRepository.getEmpleados()

                // 🚀 2. CAPTURAMOS EL ID DE LA RUTA EN ESTE MOMENTO EXACTO
                // Asegúrate de importar tu ServicioFormRoute aquí
                val idDesdeRuta = savedStateHandle.toRoute<ServicioFormRoute>().idServicio

                // 🚀 3. PREPARAMOS EL ESTADO BASE
                var newState = _uiState.value.copy(
                    todosLosServicios = serviciosReales,
                    serviciosVisibles = filtrarServicios(serviciosReales, _uiState.value.searchQuery),
                    empleadosDisponibles = empleadosReales,
                    isLoading = false
                )

                // 🚀 4. ¡LA MAGIA DE LA EDICIÓN AUTOMÁTICA!
                if (idDesdeRuta != null) {
                    val srv = serviciosReales.find { it.idServicio == idDesdeRuta }
                    if (srv != null) {
                        newState = newState.copy(
                            editandoServicioId = srv.idServicio,
                            formNombre = srv.nombre ?: "",
                            formPrecio = srv.precio?.toString() ?: "",
                            formDescripcion = srv.descripcion ?: "",
                            formEmpleadoSeleccionado = srv.empleado
                        )
                    }
                } else {
                    // MODO "CREAR": Vaciamos el formulario por si venimos de editar otro
                    newState = newState.copy(
                        editandoServicioId = null,
                        formNombre = "",
                        formPrecio = "",
                        formDescripcion = "",
                        formEmpleadoSeleccionado = null
                    )
                }

                _uiState.value = newState

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

            // 🧹 ESTOS EVENTOS YA SON ZOMBIS.
            ServiciosEvent.PrepararNuevoServicio -> { }
            is ServiciosEvent.PrepararEdicion -> { }

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

        val servicioDatos = Servicio(
            idServicio = state.editandoServicioId ?: 0L,
            nombre = state.formNombre,
            precio = state.formPrecio.replace(",", ".").toDoubleOrNull() ?: 0.0,
            descripcion = state.formDescripcion,
            empleado = empleado
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoServicioId != null) {
                    servicioRepository.updateServicio(servicioDatos)
                    _uiState.update { it.copy(mensaje = "Servicio actualizado con éxito") }
                } else {
                    servicioRepository.createServicio(servicioDatos)
                    _uiState.update { it.copy(mensaje = "Servicio guardado con éxito") }
                }

                // Recargamos los datos desde cero. Esto autolimpiará el formulario si la ruta no tiene ID.
                cargarDatos()

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
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                try {
                    servicioRepository.deleteServicio(id)
                    _uiState.update { it.copy(mensaje = "Servicio eliminado con éxito") }

                    cargarDatos()
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Error al eliminar el servicio: ${e.message}")
                    }
                }
            }
        }
    }
}