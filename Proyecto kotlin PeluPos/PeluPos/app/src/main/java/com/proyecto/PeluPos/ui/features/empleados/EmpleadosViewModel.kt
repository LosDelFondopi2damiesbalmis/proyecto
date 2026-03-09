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

@HiltViewModel
class EmpleadosViewModel @Inject constructor(
    private val empleadoRepository: EmpleadoRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EmpleadosUiState())
    val uiState: StateFlow<EmpleadosUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        _uiState.update {
            it.copy(
                empleados = empleadoRepository.getEmpleados(),
                localesDisponibles = localRepository.getLocales()
            )
        }
    }

    fun onEvent(event: EmpleadosEvent) {
        when (event) {
            EmpleadosEvent.CargarDatos -> cargarDatos()

            EmpleadosEvent.PrepararNuevoEmpleado -> {
                _uiState.update { it.copy(editandoEmpleadoId = null, formNombre = "", formCargo = "", formEmail = "", formTelefono = "", formLocalSeleccionado = null) }
            }

            is EmpleadosEvent.PrepararEdicion -> {
                val empleado = _uiState.value.empleados.find { it.idEmpleado == event.idEmpleado }
                empleado?.let { emp ->
                    _uiState.update {
                        it.copy(
                            editandoEmpleadoId = emp.idEmpleado,
                            formNombre = emp.nombre,
                            formCargo = emp.cargo,
                            formEmail = emp.email,
                            formTelefono = emp.telefono.toString().replace("0", ""), // Manejo básico
                            formLocalSeleccionado = emp.local
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
        val nuevoEmpleado = Empleado(
            idEmpleado = state.editandoEmpleadoId ?: System.currentTimeMillis(),
            telefono = state.formTelefono.toLongOrNull() ?: 0L,
            email = state.formEmail,
            cargo = state.formCargo,
            nombre = state.formNombre,
            local = state.formLocalSeleccionado
        )

        if (state.editandoEmpleadoId != null) {
            empleadoRepository.updateEmpleado(nuevoEmpleado)
        } else {
            empleadoRepository.insert(nuevoEmpleado)
        }
        cargarDatos()
    }

    private fun borrarEmpleado() {
        _uiState.value.editandoEmpleadoId?.let { id ->
            empleadoRepository.delete(id)
            cargarDatos()
        }
    }
}