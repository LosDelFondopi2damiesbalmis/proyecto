package com.proyecto.PeluPos.navigation


import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyecto.PeluPos.ui.features.locales.LocalDetailScreen
import com.proyecto.PeluPos.ui.features.locales.LocalFormScreen
import com.proyecto.PeluPos.ui.features.locales.LocalesEvent
import com.proyecto.PeluPos.ui.features.locales.LocalesScreen
import com.proyecto.PeluPos.ui.features.locales.LocalesViewModel
import kotlinx.serialization.Serializable

// ==========================================
// RUTAS
// ==========================================
@Serializable
object LocalesListRoute

@Serializable
data class LocalFormRoute(
    val idLocal: Long? = null // 👈 null = Crear, Número = Editar
)

@Serializable
data class LocalDetailRoute(val idLocal: Long)

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.localesDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: (Long?) -> Unit, // 👈 Ahora acepta el ID opcional
    navigateToDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE LOCALES
    // ==========================================
    composable<LocalesListRoute> {
        val vm = hiltViewModel<LocalesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        LocalesScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            // 🚀 CREAR: Mandamos 'null' a la ruta
            navigateToForm = { id -> navigateToForm(id) },
            navigateToLocalDetail = navigateToDetail
        )
    }

    // ==========================================
    // 2. FORMULARIO DE LOCALES
    // ==========================================
    composable<LocalFormRoute> { backStackEntry ->
        // Extraemos la ruta (Hilt se encargará de pasársela al ViewModel)
        val routeData = backStackEntry.toRoute<LocalFormRoute>()

        val vm = hiltViewModel<LocalesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        LocalFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack
        )
    }

    // ==========================================
    // 3. DETALLE DEL LOCAL
    // ==========================================
    composable<LocalDetailRoute> { backStackEntry ->
        val vm = hiltViewModel<LocalesViewModel>()
        val route = backStackEntry.toRoute<LocalDetailRoute>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        val localEncontrado = state.todosLosLocales.find { it.idLocal == route.idLocal }

        LocalDetailScreen(
            local = localEncontrado,
            onBack = onBack,
            onEditClick = {
                // 🧹 ADIÓS AL EVENTO ZOMBI: Borramos vm.onEvent(...)
                // 🚀 EDITAR: Mandamos el ID real a la navegación
                navigateToForm(route.idLocal)
            }
        )
    }
}