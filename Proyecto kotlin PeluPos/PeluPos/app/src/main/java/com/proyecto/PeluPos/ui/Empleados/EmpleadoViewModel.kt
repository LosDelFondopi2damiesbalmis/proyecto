package com.proyecto.PeluPos.ui.Empleados


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.models.Empleado
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoViewModel @Inject constructor(
    private val repository: EmpleadoRepository
) : ViewModel() {

    private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
    val empleados: StateFlow<List<Empleado>> = _empleados

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda

    private val _selectedEmployee = MutableStateFlow<Empleado?>(null)
    val selectedEmployee: StateFlow<Empleado?> = _selectedEmployee

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarEmpleados()
    }

    fun cargarEmpleados() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllEmpleados().collect { lista ->
                _empleados.value = lista
                _isLoading.value = false
            }
        }
    }

    fun buscarEmpleados(query: String) {
        viewModelScope.launch {
            _busqueda.value = query
            if (query.isBlank()) {
                cargarEmpleados()
            } else {
                repository.searchEmpleados(query).collect { resultados ->
                    _empleados.value = resultados
                }
            }
        }
    }

    fun toggleActivo(empleadoId: Long) {
        viewModelScope.launch {
            repository.toggleActivo(empleadoId)
            // Actualizar la lista local
            _empleados.value = _empleados.value.map { empleado ->
                if (empleado.id == empleadoId) {
                    empleado.copy(activo = !empleado.activo)
                } else {
                    empleado
                }
            }
        }
    }

    fun loadEmployee(id: Long) {
        viewModelScope.launch {
            repository.getEmpleadoById(id).collect { empleado ->
                _selectedEmployee.value = empleado
            }
        }
    }

    fun saveEmployee(empleado: Empleado) {
        viewModelScope.launch {
            if (empleado.id == 0L) {
                repository.insertEmpleado(empleado)
            } else {
                repository.updateEmpleado(empleado)
            }
            cargarEmpleados()
        }
    }

    fun deleteEmployee(id: Long) {
        viewModelScope.launch {
            repository.deleteEmpleado(id)
            cargarEmpleados()
        }
    }

    fun clearSelectedEmployee() {
        _selectedEmployee.value = null
    }

    fun getEmpleado(id: Long): Flow<Empleado?> {
        return repository.getEmpleadoById(id)
    }
}