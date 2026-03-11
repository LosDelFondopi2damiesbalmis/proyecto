package com.proyecto.PeluPos.ui.features.servicios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mappers.toEntity
import com.proyecto.PeluPos.data.mappers.toModel
import com.proyecto.PeluPos.data.room.dao.EmpleadoDao
import com.proyecto.PeluPos.data.room.dao.ServicioDao
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
    private val servicioDao: ServicioDao,
    private val empleadoDao: EmpleadoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServiciosUiState())
    val uiState: StateFlow<ServiciosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            // Obtener todos los servicios y mapear a modelos
            val serviciosEntity = servicioDao.getAll()
            val servicios: List<Servicio> = serviciosEntity.mapNotNull { servicioEntity ->
                val empleadoEntity = empleadoDao.getById(servicioEntity.empleadoId)
                val empleado = empleadoEntity?.toModel()
                empleado?.let { e -> servicioEntity.toModel(e) }
            }

            // Obtener todos los empleados para el desplegable
            val empleados = empleadoDao.getAll().map { it.toModel() }

            _uiState.update {
                it.copy(
                    todosLosServicios = servicios,
                    serviciosVisibles = filtrarServicios(servicios, it.searchQuery),
                    empleadosDisponibles = empleados
                )
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
                    it.copy(
                        editandoServicioId = null,
                        formNombre = "",
                        formPrecio = "",
                        formDescripcion = "",
                        formEmpleadoSeleccionado = null
                    )
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
            idServicio = state.editandoServicioId ?: 0L,
            nombre = state.formNombre,
            precio = state.formPrecio.replace(",", ".").toDoubleOrNull() ?: 0.0,
            descripcion = state.formDescripcion,
            empleado = empleado
        )

        viewModelScope.launch {
            if (state.editandoServicioId != null && state.editandoServicioId != 0L) {
                servicioDao.update(nuevoServicio.toEntity())
            } else {
                servicioDao.insert(nuevoServicio.toEntity())
            }
            cargarDatos()
        }
    }

    private fun borrarServicio() {
        val state = _uiState.value
        state.editandoServicioId?.let { id ->
            val servicio = state.todosLosServicios.find { it.idServicio == id }
            servicio?.let {
                viewModelScope.launch {
                    servicioDao.delete(it.toEntity())
                    cargarDatos()
                }
            }
        }
    }
}