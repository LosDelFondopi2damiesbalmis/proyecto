package com.proyecto.PeluPos.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.proyecto.PeluPos.ui.features.tpv.TpvViewModel
import com.proyecto.PeluPos.ui.features.ventas.FacturacionViewModel

// import androidx.hilt.navigation.compose.hiltViewModel // Para cuando uses Hilt

@Composable
fun NavHost(toggleSidebar: () -> Unit) {
    val navController = rememberNavController()
    val vmTpv = hiltViewModel<TpvViewModel>()
    val sharedViewModel = hiltViewModel<FacturacionViewModel>()


    NavHost(
        navController = navController,
        startDestination = TpvHomeRoute
    ) {

        // ==========================================
        // GRAFO 1: TPV
        // ==========================================
        tpvDestination(
            vm = vmTpv,
            toggleSidebar = toggleSidebar,
            navigateToSales = {
                navController.navigate(VentasRoute)
            },
            navigateToCreateInvoice = { _, _ ->

                navController.navigate(AddSaleRoute)
            }
        )

        salesDestination(
            vm = sharedViewModel,
            toggleSidebar = toggleSidebar,
            navigateToNewSale = {
                navController.navigate(AddSaleRoute)
            },
            navigateToSaleDetail = { idFactura ->

                navController.navigate(DetalleVentaRoute(idFactura))
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}