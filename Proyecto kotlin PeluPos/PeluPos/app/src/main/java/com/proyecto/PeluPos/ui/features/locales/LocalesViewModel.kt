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

    // --- AQUÍ USAMOS TUS REPOSITORIOS PARA LEER DESDE RETROFIT ---
    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Pedimos los datos reales asíncronamente a Tomcat
                val localesReales = localRepository.getLocales()
                val empleadosReales = empleadoRepository.getEmpleados()

                _uiState.update {
                    it.copy(
                        todosLosLocales = localesReales,
                        localesVisibles = filtrarLocales(lista = localesReales, query = it.searchQuery),
                        empleadosDisponibles = empleadosReales,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // Captura fallos de red o errores de Tomcat (como un 401 sin token)
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al cargar datos: ${e.message}")
                }
            }
        }
    }

    private fun filtrarLocales(lista: List<Local>, query: String): List<Local> {
        if (query.isBlank()) return lista
        return lista.filter {
            (it.nombre?.contains(query, ignoreCase = true) ?: false) ||
                    (it.direccion?.contains(query, ignoreCase = true) ?: false)
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
                        formEmpleadosSeleccionados = emptyList(),
                        mensaje = null,
                        error = null
                    )
                }
            }

            is LocalesEvent.PrepararEdicion -> {
                val local = _uiState.value.todosLosLocales.find { it.idLocal == event.idLocal }
                local?.let { loc ->
                    _uiState.update {
                        it.copy(
                            editandoLocalId = loc.idLocal,
                            formNombre = loc.nombre ?: "",
                            formDireccion = loc.direccion ?: "",
                            formEmpleadosSeleccionados = loc.empleadoCollection?.toList() ?: emptyList(),
                            mensaje = null,
                            error = null
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
                    // 🚀 Migrado a corrutina para llamar de forma segura a la API
                    viewModelScope.launch {
                        _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                        try {
                            localRepository.deleteLocal(id)
                            _uiState.update { it.copy(mensaje = "Local eliminado correctamente") }

                            cargarDatos() // Recargamos lista completa de Tomcat
                            onEvent(LocalesEvent.PrepararNuevoLocal) // Limpiamos campos
                        } catch (e: Exception) {
                            _uiState.update {
                                it.copy(isLoading = false, error = "No se pudo eliminar: ${e.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    // --- AQUÍ USAMOS TU REPOSITORIO PARA GUARDAR EN INTERNET VIA RETROFIT ---
    private fun guardarLocal() {
        val state = _uiState.value

        // Mapeamos los datos recogidos del estado del formulario
        val localDatos = Local(
            // ⚠️ Importante: Mandamos 0L en nuevos para activar el AUTO_INCREMENT de MySQL
            idLocal = state.editandoLocalId ?: 0L,
            nombre = state.formNombre,
            direccion = state.formDireccion,
            empleadoCollection = state.formEmpleadosSeleccionados.toMutableList()
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoLocalId != null) {
                    // 🔄 ACTUALIZAR (PUT): Enviamos el ID de la ruta de Retrofit y el cuerpo
                    localRepository.updateLocal(state.editandoLocalId, localDatos)
                    _uiState.update { it.copy(mensaje = "Local actualizado con éxito") }
                } else {
                    // ➕ CREAR (POST): Enviamos los datos del nuevo local a Tomcat
                    localRepository.createLocal(localDatos)
                    _uiState.update { it.copy(mensaje = "Local guardado con éxito") }
                }

                cargarDatos() // Volvemos a traer los datos actualizados del servidor
                onEvent(LocalesEvent.PrepararNuevoLocal) // Reseteamos el formulario

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al procesar: ${e.message}")
                }
            }
        }
    }
}