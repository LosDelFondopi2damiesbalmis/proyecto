package com.proyecto.PeluPos.ui.features.locales

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.local.LocalRepository
import com.proyecto.PeluPos.models.Local
import com.proyecto.PeluPos.navigation.LocalFormRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalesViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val empleadoRepository: EmpleadoRepository,
    private val savedStateHandle: SavedStateHandle // 🚀 1. INYECTAMOS LA ANTENA
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalesUiState())
    val uiState: StateFlow<LocalesUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // Pedimos los datos reales asíncronamente a Tomcat
                val localesReales = localRepository.getLocales()
                val empleadosReales = empleadoRepository.getEmpleados()

                // 🚀 2. CAPTURAMOS EL ID DE LA RUTA EN ESTE MOMENTO EXACTO
                // Asegúrate de importar tu LocalFormRoute aquí
                val idDesdeRuta = savedStateHandle.toRoute<LocalFormRoute>().idLocal

                // 🚀 3. PREPARAMOS EL ESTADO BASE
                var newState = _uiState.value.copy(
                    todosLosLocales = localesReales,
                    localesVisibles = filtrarLocales(lista = localesReales, query = _uiState.value.searchQuery),
                    empleadosDisponibles = empleadosReales,
                    isLoading = false
                )

                // 🚀 4. ¡LA MAGIA DE LA EDICIÓN AUTOMÁTICA!
                if (idDesdeRuta != null) {
                    val loc = localesReales.find { it.idLocal == idDesdeRuta }
                    if (loc != null) {
                        newState = newState.copy(
                            editandoLocalId = loc.idLocal,
                            formNombre = loc.nombre ?: "",
                            formDireccion = loc.direccion ?: "",
                            formEmpleadosSeleccionados = loc.empleadoCollection?.toList() ?: emptyList()
                        )
                    }
                } else {
                    // MODO "CREAR": Nos aseguramos de que el formulario esté completamente vacío
                    newState = newState.copy(
                        editandoLocalId = null,
                        formNombre = "",
                        formDireccion = "",
                        formEmpleadosSeleccionados = emptyList()
                    )
                }

                // Guardamos el estado final para que la pantalla lo dibuje
                _uiState.value = newState

            } catch (e: Exception) {
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

            // 🧹 ESTOS EVENTOS YA SON ZOMBIS.
            // Ya no necesitas dispararlos desde la UI porque la ruta lo hace todo.
            LocalesEvent.PrepararNuevoLocal -> { }
            is LocalesEvent.PrepararEdicion -> { }

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
                    viewModelScope.launch {
                        _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                        try {
                            localRepository.deleteLocal(id)
                            _uiState.update { it.copy(mensaje = "Local eliminado correctamente") }
                            cargarDatos()
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

    private fun guardarLocal() {
        val state = _uiState.value

        val localDatos = Local(
            idLocal = state.editandoLocalId ?: 0L,
            nombre = state.formNombre,
            direccion = state.formDireccion,
            empleadoCollection = state.formEmpleadosSeleccionados
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoLocalId != null) {
                    localRepository.updateLocal(localDatos) // Quité el duplicado que había aquí

                    // 🚀 2. LA MAGIA NEGRA DESDE ANDROID: Averiguamos quién entra y quién sale
                    // Buscamos cómo estaba el local antes de que el usuario tocara nada
                    val localOriginal = state.todosLosLocales.find { it.idLocal == state.editandoLocalId }
                    val empleadosAntes = localOriginal?.empleadoCollection ?: emptyList()
                    val empleadosAhora = state.formEmpleadosSeleccionados

                    // ¿A quién hemos añadido nuevo? (Le ponemos este local)
                    val añadidos = empleadosAhora.filter { nuevo ->
                        empleadosAntes.none { it.idEmpleado == nuevo.idEmpleado }
                    }

                    // ¿A quién hemos quitado en la pantalla? (Le ponemos local = null)
                    val quitados = empleadosAntes.filter { viejo ->
                        empleadosAhora.none { it.idEmpleado == viejo.idEmpleado }
                    }

                    añadidos.forEach { empleado ->
                        empleadoRepository.updateEmpleado(empleado.copy(idLocal = localDatos))
                    }

                    quitados.forEach { empleado ->
                        empleadoRepository.updateEmpleado(empleado.copy(idLocal = null))
                    }

                    _uiState.update { it.copy(mensaje = "Local y equipo actualizados con éxito") }
                } else {
                    localRepository.createLocal(localDatos)
                    _uiState.update { it.copy(mensaje = "Local guardado con éxito") }
                }

                // Recargamos los datos para ver los cambios reflejados instantáneamente
                cargarDatos()

                // --- ATRAPAMOS EL JSON DE LA API (Ej: 403 Acceso Denegado) ---
            } catch (e: retrofit2.HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                var mensajeError = "Error en el servidor (${e.code()})"

                if (!jsonString.isNullOrEmpty()) {
                    try {
                        val jsonObject = org.json.JSONObject(jsonString)
                        if (jsonObject.has("mensaje")) {
                            mensajeError = jsonObject.getString("mensaje")
                        }
                    } catch (parseException: Exception) {
                        mensajeError = jsonString
                    }
                }

                _uiState.update { it.copy(isLoading = false, error = mensajeError) }

                // --- ATRAPAMOS OTROS ERRORES (Ej: Sin internet) ---
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al procesar: ${e.message}")
                }
            }
        }
    }
}