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

// ==========================================
// RUTAS (Data Classes preparadas para ID)
// ==========================================
@Serializable
object EmpleadosListRoute

@Serializable
data class EmpleadoFormRoute(
    val idEmpleado: Long? = null // 👈 null = Crear nuevo | Número = Editar
)

@Serializable
data class EmpleadoStatsRoute(val idEmpleado: Long)

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.empleadosDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: (Long?) -> Unit, // 👈 Ahora acepta un ID opcional
    navigateToStats: (Long) -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE EMPLEADOS
    // ==========================================
    composable<EmpleadosListRoute> {
        // NACE EL VIEWMODEL DE LA LISTA
        val vm = hiltViewModel<EmpleadosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        EmpleadosScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            // 🚀 CREAR: Mandamos 'null' a la ruta
            onNavigateToCreate = { navigateToForm(null) },
            // 🚀 EDITAR: Mandamos el ID real del empleado
            onNavigateToEdit = { idEmpleado -> navigateToForm(idEmpleado) },
            onNavigateToStats = navigateToStats
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<EmpleadoFormRoute> { backStackEntry ->
        // 1. Extraemos los datos de la URL de navegación
        val routeData = backStackEntry.toRoute<EmpleadoFormRoute>()

        // 2. NACE UN NUEVO VIEWMODEL LIMPIO PARA EL FORMULARIO
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
        val route = backStackEntry.toRoute<EmpleadoStatsRoute>()

        EmpleadoStatsScreen(
            empleadoId = route.idEmpleado,
            onBackClick = onBack
        )
    }
}