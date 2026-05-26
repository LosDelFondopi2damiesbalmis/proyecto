package com.proyecto.PeluPos.ui.features.empleados

import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.local.LocalRepository
import com.proyecto.PeluPos.models.Empleado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.proyecto.PeluPos.navigation.EmpleadoFormRoute

@HiltViewModel
class EmpleadosViewModel @Inject constructor(
    private val empleadoRepository: EmpleadoRepository,
    private val localRepository: LocalRepository,
    savedStateHandle: SavedStateHandle // 🚀 1. INYECTAMOS LA ANTENA PARA ESCUCHAR LA RUTA
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmpleadosUiState())
    val uiState: StateFlow<EmpleadosUiState> = _uiState.asStateFlow()

    // 🚀 2. CAPTURAMOS EL ID DE LA RUTA MÁGICAMENTE (Si es null = Crear, Si tiene número = Editar)
    // Nota: Asegúrate de importar tu EmpleadoFormRoute
    private val idEmpleadoAEditar = savedStateHandle.toRoute<EmpleadoFormRoute>().idEmpleado

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val listaEmpleados = empleadoRepository.getEmpleados()
                val listaLocales = localRepository.getLocales()

                // 🚀 3. PREPARAMOS EL ESTADO BASE
                var newState = _uiState.value.copy(
                    empleados = listaEmpleados,
                    localesDisponibles = listaLocales,
                    isLoading = false
                )

                // 🚀 4. ¡LA MAGIA DE LA EDICIÓN!
                // Si la ruta nos pasó un ID, buscamos al empleado y rellenamos el formulario
                if (idEmpleadoAEditar != null) {
                    val emp = listaEmpleados.find { it.idEmpleado == idEmpleadoAEditar }
                    if (emp != null) {
                        newState = newState.copy(
                            editandoEmpleadoId = emp.idEmpleado,
                            formNombre = emp.nombre ?: "",
                            formCargo = emp.cargo ?: "",
                            formEmail = emp.email ?: "",
                            formTelefono = emp.telefono?.toString() ?: "",
                            formLocalSeleccionado = emp.idLocal
                        )
                    }
                }

                // Guardamos el estado final para que la pantalla lo dibuje
                _uiState.value = newState

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar datos: ${e.message}"
                    )
                }
            }
        }
    }

    fun onEvent(event: EmpleadosEvent) {
        when (event) {
            EmpleadosEvent.CargarDatos -> cargarDatos()

            // 🧹 Estos dos eventos (PrepararNuevoEmpleado y PrepararEdicion)
            // ya son eventos zombis reales. Ya no se usan nunca desde la UI.
            // Los dejo aquí para que no te dé error el archivo EmpleadosEvent,
            // pero podrías borrarlos tranquilamente de tu sealed class en el futuro.
            EmpleadosEvent.PrepararNuevoEmpleado -> { /* Ya no hace falta */ }
            is EmpleadosEvent.PrepararEdicion -> { /* Ya no hace falta */ }

            is EmpleadosEvent.OnNombreChange -> _uiState.update { it.copy(formNombre = event.nombre) }
            is EmpleadosEvent.OnCargoChange -> _uiState.update { it.copy(formCargo = event.cargo) }
            is EmpleadosEvent.OnEmailChange -> _uiState.update { it.copy(formEmail = event.email) }
            is EmpleadosEvent.OnTelefonoChange -> _uiState.update { it.copy(formTelefono = event.telefono) }
            is EmpleadosEvent.OnLocalChange -> _uiState.update { it.copy(formLocalSeleccionado = event.local) }

            EmpleadosEvent.GuardarEmpleado -> guardarEmpleado()
            EmpleadosEvent.BorrarEmpleado -> borrarEmpleado()
        }
    }

    private fun guardarEmpleado() {
        val state = _uiState.value

        val empleadoDatos = Empleado(
            idEmpleado = state.editandoEmpleadoId ?: 0L,
            telefono = state.formTelefono.toLongOrNull() ?: 0L,
            email = state.formEmail,
            cargo = state.formCargo,
            nombre = state.formNombre,
            idLocal = state.formLocalSeleccionado
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoEmpleadoId != null) {
                    empleadoRepository.updateEmpleado(empleadoDatos)
                    _uiState.update { it.copy(mensaje = "Empleado actualizado con éxito") }
                } else {
                    empleadoRepository.createEmpleado(empleadoDatos)
                    _uiState.update { it.copy(mensaje = "Empleado guardado con éxito") }
                }

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
                    it.copy(isLoading = false, error = "Error al guardar: ${e.message}")
                }
            }
        }
    }
    fun cargarEstadisticas(idEmpleado: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingStats = true, statsMes = null) } // Limpiamos datos anteriores
            try {
                val stats = empleadoRepository.getResumenVentas(idEmpleado)
                _uiState.update { it.copy(statsMes = stats, isLoadingStats = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoadingStats = false) } // Aquí podrías manejar el error
            }
        }
    }

    private fun borrarEmpleado() {
        val id = _uiState.value.editandoEmpleadoId
        if (id != null) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                try {
                    empleadoRepository.deleteEmpleado(id)
                    _uiState.update { it.copy(mensaje = "Empleado eliminado con éxito") }
                    cargarDatos()
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Error al borrar: ${e.message}")
                    }
                }
            }
        }
    }
}