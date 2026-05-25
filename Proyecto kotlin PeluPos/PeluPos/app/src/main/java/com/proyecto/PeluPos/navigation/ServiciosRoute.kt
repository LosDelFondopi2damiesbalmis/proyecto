package com.proyecto.PeluPos.navigation

import ServiciosScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.features.servicios.ServicioFormScreen
import com.proyecto.PeluPos.ui.features.servicios.ServiciosViewModel

import androidx.navigation.toRoute

// ==========================================
// RUTAS
// ==========================================
@Serializable
object ServiciosListRoute

@Serializable
data class ServicioFormRoute(
    val idServicio: Long? = null // 👈 null = Crear nuevo, Número = Editar
)

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.serviciosDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: (Long?) -> Unit, // 👈 Ahora acepta el ID opcional
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE SERVICIOS
    // ==========================================
    composable<ServiciosListRoute> {
        val vm = hiltViewModel<ServiciosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ServiciosScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToForm = navigateToForm // La pasamos directamente a la vista
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ServicioFormRoute> { backStackEntry ->
        // Extraemos la ruta (Hilt inyectará el ID automáticamente en el ViewModel)
        val routeData = backStackEntry.toRoute<ServicioFormRoute>()

        val vm = hiltViewModel<ServiciosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ServicioFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack
        )
    }
}