package com.proyecto.PeluPos.ui.Empleados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository

class EmpleadoViewModelFactory(
    private val repository: EmpleadoRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmpleadoViewModel::class.java)) {
            return EmpleadoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}