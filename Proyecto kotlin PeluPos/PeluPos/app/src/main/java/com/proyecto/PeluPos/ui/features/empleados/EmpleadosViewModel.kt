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

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class EmpleadosViewModel @Inject constructor(
    private val empleadoRepository: EmpleadoRepository,
    private val localRepository: LocalRepository // Asumo que este también tendrá corrutinas o BD local
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmpleadosUiState())
    val uiState: StateFlow<EmpleadosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        // 🚀 Usamos viewModelScope.launch porque ir a internet requiere corrutinas
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val listaEmpleados = empleadoRepository.getEmpleados()
                // Si tu localRepository también viaja a internet, ponle un 'suspend' y llámalo aquí igual
                val listaLocales = localRepository.getLocales()

                _uiState.update {
                    it.copy(
                        empleados = listaEmpleados,
                        localesDisponibles = listaLocales,
                        isLoading = false
                    )
                }
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

            EmpleadosEvent.PrepararNuevoEmpleado -> {
                _uiState.update {
                    it.copy(
                        editandoEmpleadoId = null,
                        formNombre = "",
                        formCargo = "",
                        formEmail = "",
                        formTelefono = "",
                        formLocalSeleccionado = null,
                        mensaje = null,
                        error = null
                    )
                }
            }

            is EmpleadosEvent.PrepararEdicion -> {
                val empleado = _uiState.value.empleados.find { it.idEmpleado == event.idEmpleado }
                empleado?.let { emp ->
                    _uiState.update {
                        it.copy(
                            editandoEmpleadoId = emp.idEmpleado,
                            formNombre = emp.nombre ?: "",
                            formCargo = emp.cargo ?: "",
                            formEmail = emp.email ?: "",
                            formTelefono = emp.telefono?.toString() ?: "",
                            formLocalSeleccionado = emp.local,
                            mensaje = null,
                            error = null
                        )
                    }
                }
            }

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

        // Creamos el objeto empleado mapeando los campos del formulario
        val empleadoDatos = Empleado(
            // Al crear pasamos 0L (o null si tu modelo en Kotlin lo permite) para que MySQL autoincremente
            idEmpleado = state.editandoEmpleadoId ?: 0L,
            telefono = state.formTelefono.toLongOrNull() ?: 0L,
            email = state.formEmail,
            cargo = state.formCargo,
            nombre = state.formNombre,
            local = state.formLocalSeleccionado
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoEmpleadoId != null) {
                    // 🔄 ACTUALIZAR: Mandamos el ID de la ruta y los datos nuevos al PUT de Retrofit
                    empleadoRepository.updateEmpleado(state.editandoEmpleadoId, empleadoDatos)
                    _uiState.update { it.copy(mensaje = "Empleado actualizado con éxito") }
                } else {
                    // ➕ CREAR: Mandamos los datos al POST de Retrofit
                    empleadoRepository.createEmpleado(empleadoDatos)
                    _uiState.update { it.copy(mensaje = "Empleado guardado con éxito") }
                }

                // Limpiamos el formulario y refrescamos la lista con lo que hay en Tomcat
                cargarDatos()
                onEvent(EmpleadosEvent.PrepararNuevoEmpleado)

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Error al guardar: ${e.message}")
                }
            }
        }
    }

    private fun borrarEmpleado() {
        val id = _uiState.value.editandoEmpleadoId
        if (id != null) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                try {
                    // ❌ BORRAR: Llamamos al DELETE pasándole el ID
                    empleadoRepository.deleteEmpleado(id)
                    _uiState.update { it.copy(mensaje = "Empleado eliminado con éxito") }

                    cargarDatos()
                    onEvent(EmpleadosEvent.PrepararNuevoEmpleado)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Error al borrar: ${e.message}")
                    }
                }
            }
        }
    }
}