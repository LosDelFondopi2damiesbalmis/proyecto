package com.proyecto.PeluPos.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyecto.PeluPos.ui.features.empleados.EmpleadoFormScreen
import com.proyecto.PeluPos.ui.features.empleados.EmpleadoStatsScreen
import com.proyecto.PeluPos.ui.features.empleados.EmpleadosScreen
import com.proyecto.PeluPos.ui.features.empleados.EmpleadosViewModel
import kotlinx.serialization.Serializable

@Serializable
object EmpleadosListRoute

@Serializable
object EmpleadoFormRoute

@Serializable
data class EmpleadoStatsRoute(val idEmpleado: Long)

fun NavGraphBuilder.empleadosDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit,
    navigateToStats: (Long) -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE EMPLEADOS
    // ==========================================
    composable<EmpleadosListRoute> {
        val vm = hiltViewModel<EmpleadosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        EmpleadosScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            onNavigateToCreate = navigateToForm,
            onNavigateToEdit = navigateToForm, // El evento de preparar edición ya se llama dentro del Screen
            onNavigateToStats = navigateToStats
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<EmpleadoFormRoute> {
        val vm = hiltViewModel<EmpleadosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        EmpleadoFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onBackClick = onBack
        )
    }

    // ==========================================
    // 3. ESTADÍSTICAS DEL EMPLEADO
    // ==========================================
    composable<EmpleadoStatsRoute> { backStackEntry ->
        // Extraemos el ID de forma 100% segura
        val route = backStackEntry.toRoute<EmpleadoStatsRoute>()

        EmpleadoStatsScreen(
            empleadoId = route.idEmpleado,
            onBackClick = onBack
        )
    }
}