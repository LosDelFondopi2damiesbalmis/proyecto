package com.proyecto.PeluPos.ui.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proyecto.PeluPos.ui.products.NewProductScreen
import com.proyecto.PeluPos.ui.products.Product
import com.proyecto.PeluPos.ui.products.ProductsScreen

fun NavGraphBuilder.productsGraph(
    navController: NavController,
    products: SnapshotStateList<Product>, // Tu lista de datos sin ViewModel
    toggleSidebar: () -> Unit
) {
    // 1. La Lista
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

    // 2. Nuevo Producto
    composable(Screen.NewProduct.route) {
        NewProductScreen(
            onBackClick = { navController.popBackStack() },
            onSaveProduct = { newProduct ->
                products.add(newProduct)
                navController.popBackStack()
            }
        )
    }

    // 3. Detalle (Opcional si ya lo tienes hecho)
    composable(
        route = Screen.ProductDetail.route,
        arguments = listOf(navArgument("productId") { type = NavType.StringType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("productId")
        // ProductDetailScreen(productId = id...)
    }
}