package com.proyecto.PeluPos.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.features.ventas.VentasScreen


fun NavGraphBuilder.salesDestination(
    navController: NavController,
    toggleSidebar: () -> Unit
) {
    composable(Screen.Sales.route) {
        // Asumiendo que tienes una pantalla SalesScreen
        VentasScreen(
            toggleSidebar = toggleSidebar,
            navigateToNewSale = { navController.navigate(Screen.NewSale.route) },
            navigateToSaleDetail = { id -> navController.navigate("sale_detail/$id") }
        )
    }

    composable(Screen.NewSale.route) {
        // NewSaleScreen(...)
    }
}