package com.proyecto.PeluPos.ui.navigation

import ServicesScreen
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proyecto.PeluPos.locales.EditLocalScreen
import com.proyecto.PeluPos.locales.LocalesScreen
import com.proyecto.PeluPos.ui.DashboardPage
import com.proyecto.PeluPos.ui.products.NewProductScreen
import com.proyecto.PeluPos.ui.products.Product
import com.proyecto.PeluPos.ui.products.ProductsScreen

fun NavGraphBuilder.appNavigation(
    navController: NavHostController,
    isSidebarVisible: Boolean,
    toggleSidebar: () -> Unit,
    products: SnapshotStateList<Product> // Recibimos la lista para manipularla
) {
    // --- DASHBOARD ---
    composable(Screen.Dashboard.route) {
        DashboardPage(toggleSidebar = toggleSidebar)
    }

    // --- PRODUCTOS (Flujo completo) ---
    composable(Screen.Products.route) {
        ProductsScreen(
            toggleSidebar = toggleSidebar,
            navigateToProductDetail = { productId ->
                navController.navigate(Screen.ProductDetail.createRoute(productId))
            },
            navigateToNewProduct = {
                navController.navigate(Screen.NewProduct.route)
            }
        )
    }

    composable(Screen.NewProduct.route) {
        NewProductScreen(
            onBackClick = { navController.popBackStack() },
            onSaveProduct = { newProduct ->
                products.add(newProduct) // Lógica temporal
                navController.popBackStack()
            }
        )
    }

    composable(
        route = Screen.ProductDetail.route,
        arguments = listOf(navArgument("productId") { type = NavType.StringType })
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("productId")
        // Aquí llamarías a ProductDetailScreen(productId)
    }

    // --- CLIENTES ---
    composable(Screen.Clients.route) {
        // ClienteScreen(toggleSidebar = toggleSidebar...)
    }

    // --- LOCALES (Flujo con ID entero) ---
    composable(Screen.Locations.route) {
        LocalesScreen(
            toggleSidebar = toggleSidebar,
            navigateToNewLocal = { navController.navigate(Screen.NewLocal.route) },
            navigateToLocalDetail = { localId ->
                navController.navigate(Screen.EditLocal.createRoute(localId))
            },
            navigateToEditLocal = { localId ->
                navController.navigate(Screen.EditLocal.createRoute(localId))
            }
        )
    }

    composable(Screen.NewLocal.route) {
        // CreateLocalScreen(...)
    }

    composable(
        route = Screen.EditLocal.route,
        arguments = listOf(navArgument("localId") { type = NavType.IntType })
    ) { backStackEntry ->
        // Recuperamos el ID de forma segura
        val localId = backStackEntry.arguments?.getInt("localId") ?: 0

        EditLocalScreen(
            localId = localId,
            onNavigateBack = { navController.popBackStack() },
            onSaveSuccess = { navController.popBackStack() }
        )
    }

    // --- RESTO DE PANTALLAS ---
    composable(Screen.Services.route) { ServicesScreen(
        toggleSidebar = toggleSidebar,
        navigateToNewService = {
            // Navegar al formulario de creación
            navController.navigate("new_service") // O Screen.NewService.route si lo creas
        },
        navigateToServiceDetail = { serviceId ->
            // Navegar a la edición/detalle pasando el ID
            navController.navigate("edit_service/$serviceId")
        }
    ) }
    composable(Screen.Employees.route) { /* ... */ }
    composable(Screen.Sales.route) { /* ... */ }
    composable(Screen.Users.route) { /* ... */ }
}