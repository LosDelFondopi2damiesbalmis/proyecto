package com.proyecto.PeluPos.ui.features.locales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.room.dao.LocalDao
import com.proyecto.PeluPos.data.room.entity.toEntity
import com.proyecto.PeluPos.data.room.entity.toModel
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.models.Local
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalesViewModel @Inject constructor(
    private val localDao: LocalDao,
    private val empleadoRepository: EmpleadoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalesUiState())
    val uiState: StateFlow<LocalesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            localDao.getAllLocalesFlow()
                .map { lista -> lista.map { it.toModel() } }
                .collect { listaLocales ->

                    val empleadosReales = empleadoRepository.getEmpleados()

                    _uiState.update {
                        it.copy(
                            todosLosLocales = listaLocales,
                            localesVisibles = filtrarLocales(listaLocales, it.searchQuery),
                            empleadosDisponibles = empleadosReales
                        )
                    }
                }
        }
    }

    private fun filtrarLocales(lista: List<Local>, query: String): List<Local> {
        if (query.isBlank()) return lista
        return lista.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.direccion.contains(query, ignoreCase = true)
        }
    }

    fun onEvent(event: LocalesEvent) {
        when (event) {

            LocalesEvent.CargarDatos -> {} // Flow ya actualiza solo

            is LocalesEvent.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        localesVisibles = filtrarLocales(it.todosLosLocales, event.query)
                    )
                }
            }

            LocalesEvent.PrepararNuevoLocal -> {
                _uiState.update {
                    it.copy(
                        editandoLocalId = null,
                        formNombre = "",
                        formDireccion = "",
                        formEmpleadosSeleccionados = emptyList()
                    )
                }
            }

            is LocalesEvent.PrepararEdicion -> {

                val local = _uiState.value.todosLosLocales
                    .find { it.idLocal == event.idLocal }

                local?.let { loc ->
                    _uiState.update {
                        it.copy(
                            editandoLocalId = loc.idLocal,
                            formNombre = loc.nombre,
                            formDireccion = loc.direccion,
                            formEmpleadosSeleccionados = loc.empleados.toList()
                        )
                    }
                }
            }

            is LocalesEvent.OnNombreChange ->
                _uiState.update { it.copy(formNombre = event.nombre) }

            is LocalesEvent.OnDireccionChange ->
                _uiState.update { it.copy(formDireccion = event.direccion) }

            is LocalesEvent.OnAddEmpleado -> {

                val seleccionados =
                    _uiState.value.formEmpleadosSeleccionados.toMutableList()

                if (!seleccionados.contains(event.empleado)) {
                    seleccionados.add(event.empleado)

                    _uiState.update {
                        it.copy(formEmpleadosSeleccionados = seleccionados)
                    }
                }
            }

            is LocalesEvent.OnRemoveEmpleado -> {

                val seleccionados =
                    _uiState.value.formEmpleadosSeleccionados.toMutableList()

                seleccionados.remove(event.empleado)

                _uiState.update {
                    it.copy(formEmpleadosSeleccionados = seleccionados)
                }
            }

            LocalesEvent.GuardarLocal -> guardarLocal()

            is LocalesEvent.BorrarLocal ->
                borrarLocal(event.idLocal)
        }
    }

    private fun guardarLocal() {

        val state = _uiState.value

        val nuevoLocal = Local(
            idLocal = state.editandoLocalId ?: 0L,
            nombre = state.formNombre,
            direccion = state.formDireccion,
            empleados = state.formEmpleadosSeleccionados.toMutableList()
        )

        viewModelScope.launch {

            if (state.editandoLocalId != null) {

                localDao.updateLocal(nuevoLocal.toEntity())

            } else {

                localDao.insertLocal(nuevoLocal.toEntity())

            }
        }
    }

    private fun borrarLocal(idLocal: Long) {

        viewModelScope.launch {

            val local = _uiState.value.todosLosLocales
                .find { it.idLocal == idLocal }

            local?.let {

                localDao.deleteLocal(it.toEntity())

            }
        }
    }
}