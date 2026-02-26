// ui/navigation/Navigation.kt
package com.proyecto.PeluPos.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

// Definición de las pantallas
sealed class Screen(val route: String) {
    // Pantallas principales
    object Dashboard : Screen("dashboard")
    object Clients : Screen("clients")
    object Services : Screen("services")
    object Products : Screen("products")
    object Employees : Screen("employees")
    object Sales : Screen("sales")
    object Users : Screen("users")
    object Locations : Screen("locations")

    // Pantallas de Empleados
    object EmployeeDetail : Screen("employee_detail/{employeeId}") {
        fun createRoute(employeeId: Long) = "employee_detail/$employeeId"
    }

    object NewEmployee : Screen("new_employee")

    object EditEmployee : Screen("edit_employee/{employeeId}") {
        fun createRoute(employeeId: Long) = "edit_employee/$employeeId"
    }

    // Otras pantallas
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Long) = "product_detail/$productId"
    }

    object NewProduct : Screen("new_product")

    object NewSale : Screen("new_sale")
    object EditProduct : Screen("edit_product/{productId}") {
        fun createRoute(productId: Long) = "edit_product/$productId"
    }
    object NewLocal : Screen("new_local")

    object EditLocal : Screen("edit_local/{localId}") {
        // Helper para crear la ruta pasando el ID (ej: "edit_local/5")
        fun createRoute(localId: Int) = "edit_local/$localId"
    }
    object Tpv : Screen("tpv")
}

// Rutas principales para sidebar
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