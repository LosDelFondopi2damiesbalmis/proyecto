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

// Rutas Tipadas
@Serializable
object LocalesListRoute

@Serializable
object LocalFormRoute
@Serializable
data class LocalDetailRoute(val idLocal: Long)

fun NavGraphBuilder.localesDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    // Lista de Locales
    composable<LocalesListRoute> {
        val vm = hiltViewModel<LocalesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        LocalesScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToForm = navigateToForm,
            navigateToLocalDetail = navigateToDetail
        )
    }

    // Formulario de Locales
    composable<LocalFormRoute> {
        val vm = hiltViewModel<LocalesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        LocalFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack
        )
    }
    composable<LocalDetailRoute> { backStackEntry ->
        val vm = hiltViewModel<LocalesViewModel>()
        val route = backStackEntry.toRoute<LocalDetailRoute>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        val localEncontrado = state.todosLosLocales.find { it.idLocal == route.idLocal }

        LocalDetailScreen(
            local = localEncontrado,
            onBack = onBack,
            onEditClick = {
                vm.onEvent(LocalesEvent.PrepararEdicion(route.idLocal))
                navigateToForm()
            }
        )
    }
}