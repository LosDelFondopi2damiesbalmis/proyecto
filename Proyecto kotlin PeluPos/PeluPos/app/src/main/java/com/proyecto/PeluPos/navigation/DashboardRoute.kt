package com.proyecto.PeluPos.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.dashboard.DashboardPage
import com.proyecto.PeluPos.ui.dashboard.DashboardViewModel
import kotlinx.serialization.Serializable


@Serializable
object DashboardRoute

fun NavGraphBuilder.dashboardDestination(
    vm: DashboardViewModel,
    toggleSidebar: () -> Unit
) {
    composable<DashboardRoute> {
        // Recopilamos el estado
        val state by vm.uiState.collectAsStateWithLifecycle()

        // Cada vez que entremos al Dashboard, recargamos los datos
        // para asegurarnos de que el stock y las estadísticas estén al día
        LaunchedEffect(Unit) {
            vm.cargarDatos()
        }

        // Llamamos a la pantalla "tonta"
        DashboardPage(
            state = state,
            toggleSidebar = toggleSidebar
        )
    }
}