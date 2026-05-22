package com.proyecto.PeluPos.ui.features.locales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.local.LocalRepository
import com.proyecto.PeluPos.models.Local
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalesViewModel @Inject constructor(
    private val localRepository: LocalRepository,       // ¡Tu repo real!
    private val empleadoRepository: EmpleadoRepository  // ¡Para el selector de equipo!
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalesUiState())
    val uiState: StateFlow<LocalesUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    // --- AQUÍ USAMOS TUS REPOSITORIOS PARA LEER ---
    private fun cargarDatos() {
        // 🚀 Abrimos la corrutina para poder usar funciones "suspend"
        viewModelScope.launch {
            try {
                // Pedimos los datos reales a tus repositorios
                val localesReales = localRepository.getLocales()
                val empleadosReales = empleadoRepository.getEmpleados() // ¡Aquí ya no se quejará!

                _uiState.update {
                    it.copy(
                        todosLosLocales = localesReales,
                        // Asumo que le pasas los parámetros correctos a filtrarLocales
                        localesVisibles = filtrarLocales(lista = localesReales, query = it.searchQuery ?: ""),
                        empleadosDisponibles = empleadosReales
                    )
                }
            } catch (e: Exception) {
                // Si Tomcat está apagado o falla, lo atrapamos aquí para que no explote
                println("Error al cargar los datos: ${e.message}")
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
            LocalesEvent.CargarDatos -> cargarDatos()

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
                val local = _uiState.value.todosLosLocales.find { it.idLocal == event.idLocal }
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

            is LocalesEvent.OnNombreChange -> _uiState.update { it.copy(formNombre = event.nombre) }
            is LocalesEvent.OnDireccionChange -> _uiState.update { it.copy(formDireccion = event.direccion) }
            is LocalesEvent.OnAddEmpleado -> {
                val seleccionados = _uiState.value.formEmpleadosSeleccionados.toMutableList()
                if (!seleccionados.contains(event.empleado)) {
                    seleccionados.add(event.empleado)
                    _uiState.update { it.copy(formEmpleadosSeleccionados = seleccionados) }
                }
            }

            is LocalesEvent.OnRemoveEmpleado -> {
                val seleccionados = _uiState.value.formEmpleadosSeleccionados.toMutableList()
                seleccionados.remove(event.empleado)
                _uiState.update { it.copy(formEmpleadosSeleccionados = seleccionados) }
            }

            is LocalesEvent.GuardarLocal -> guardarLocal()
            is LocalesEvent.BorrarLocal -> {
                _uiState.value.editandoLocalId?.let { id ->
                    localRepository.delete(id)
                    cargarDatos()
                }
            }
        }
    }

    // --- AQUÍ USAMOS TU REPOSITORIO PARA GUARDAR ---
    private fun guardarLocal() {
        val state = _uiState.value
        val nuevoLocal = Local(
            idLocal = state.editandoLocalId ?: System.currentTimeMillis(),
            nombre = state.formNombre,
            direccion = state.formDireccion,
            empleados = state.formEmpleadosSeleccionados.toMutableList()
        )

        if (state.editandoLocalId != null) {
            localRepository.updateLocal(nuevoLocal) // Modificar existente
        } else {
            localRepository.insert(nuevoLocal) // Crear nuevo
        }

        cargarDatos() // ¡Recargamos la lista automáticamente!
    }
}