// ui/navigation/Navigation.kt
package com.proyecto.PeluPos.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

// Definición de las pantallas
sealed class Screen(val route: String) {
    // Pantallas principales (sin parámetros)
    object Dashboard : Screen("dashboard")
    object Clients : Screen("clients")
    object Services : Screen("services")
    object Products : Screen("products")
    object Employees : Screen("employees")
    object Sales : Screen("sales")
    object Users : Screen("users")
    object Locations : Screen("locations")

    // Pantallas con parámetros
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }

    object EmployeeDetail : Screen("employee_detail/{employeeId}") {
        fun createRoute(employeeId: String) = "employee_detail/$employeeId"
    }

    // Pantallas de formulario
    object NewProduct : Screen("new_product")
    object NewEmployee : Screen("new_employee")
    object EditProduct : Screen("edit_product/{productId}") {
        fun createRoute(productId: String) = "edit_product/$productId"
    }
}

// Rutas principales para la sidebar
val mainScreens = listOf(
    Screen.Dashboard,
    Screen.Clients,
    Screen.Services,
    Screen.Products,
    Screen.Employees,
    Screen.Sales,
    Screen.Users,
    Screen.Locations
)