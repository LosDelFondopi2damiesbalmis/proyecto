package com.proyecto.PeluPos.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.DashboardPage

fun NavGraphBuilder.dashboardDestination(
    toggleSidebar: () -> Unit
) {
    composable(Screen.Dashboard.route) {
        DashboardPage(toggleSidebar = toggleSidebar)
    }
}