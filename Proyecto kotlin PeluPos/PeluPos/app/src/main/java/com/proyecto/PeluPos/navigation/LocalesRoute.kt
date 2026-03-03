package com.proyecto.PeluPos.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.proyecto.PeluPos.ui.locales.CreateLocalScreen
import com.proyecto.PeluPos.ui.locales.EditLocalScreen
import com.proyecto.PeluPos.ui.locales.LocalesScreen

fun NavGraphBuilder.locationsGraph(
    navController: NavController,
    toggleSidebar: () -> Unit
) {
    // Lista
    composable(Screen.Locations.route) {
        LocalesScreen(
            toggleSidebar = toggleSidebar,
            navigateToNewLocal = { navController.navigate(Screen.NewLocal.route) },
            navigateToLocalDetail = { id -> navController.navigate(Screen.EditLocal.createRoute(id)) },
            navigateToEditLocal = { id -> navController.navigate(Screen.EditLocal.createRoute(id)) }
        )
    }

    // Nuevo
    composable(Screen.NewLocal.route) {
        CreateLocalScreen(
            onNavigateBack = { navController.popBackStack() },
            onSaveSuccess = {
                // Aquí podrías añadir el local a tu lista temporal si la tuvieras accesible
                // locals.add(Local(nombre, ...))

                navController.popBackStack() // Volver a la lista tras guardar
            }
        )
    }

    // Editar
    composable(
        route = Screen.EditLocal.route,
        arguments = listOf(navArgument("localId") { type = NavType.IntType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("localId") ?: 0
        EditLocalScreen(
            localId = id,
            onNavigateBack = { navController.popBackStack() },
            onSaveSuccess = { navController.popBackStack() }
        )
    }
}