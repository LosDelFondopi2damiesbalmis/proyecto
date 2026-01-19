// ui/navigation/AppNavigation.kt
package com.proyecto.PeluPos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyecto.PeluPos.ui.Empleados.DetalleEmpleadoScreen
import com.proyecto.PeluPos.ui.Empleados.EmpleadosScreen
import com.proyecto.PeluPos.ui.Empleados.EmpleadoViewModel
import com.proyecto.PeluPos.ui.Empleados.NewEditEmployeeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        // Pantalla Dashboard (puedes crear después)
        composable(Screen.Dashboard.route) {
            // DashboardScreen()
            EmpleadosScreen( // Temporalmente mostramos empleados
                onAddEmpleado = {
                    navController.navigate(Screen.NewEmployee.route)
                },
                onEmpleadoClick = { id ->
                    navController.navigate(Screen.EmployeeDetail.createRoute(id))
                }
            )
        }

        // Pantalla de Empleados
        composable(Screen.Employees.route) {
            val viewModel: EmpleadoViewModel = hiltViewModel()
            EmpleadosScreen(
                onAddEmpleado = {
                    navController.navigate(Screen.NewEmployee.route)
                },
                onEmpleadoClick = { id ->
                    navController.navigate(Screen.EmployeeDetail.createRoute(id))
                },
                viewModel = viewModel
            )
        }

        // Detalle de Empleado
        composable(
            route = Screen.EmployeeDetail.route,
            arguments = listOf(
                navArgument("employeeId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getLong("employeeId") ?: 0L
            val viewModel: EmpleadoViewModel = hiltViewModel()

            DetalleEmpleadoScreen(
                empleadoId = employeeId,
                onBack = { navController.navigateUp() },
                onEdit = {
                    navController.navigate(Screen.EditEmployee.createRoute(employeeId))
                },
                viewModel = viewModel
            )
        }

        // Nuevo Empleado
        composable(Screen.NewEmployee.route) {
            val viewModel: EmpleadoViewModel = hiltViewModel()

            NewEditEmployeeScreen(
                employeeId = null,
                onSave = {
                    navController.popBackStack(Screen.Employees.route, false)
                },
                onCancel = {
                    navController.navigateUp()
                },
                viewModel = viewModel
            )
        }

        // Editar Empleado
        composable(
            route = Screen.EditEmployee.route,
            arguments = listOf(
                navArgument("employeeId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getLong("employeeId") ?: 0L
            val viewModel: EmpleadoViewModel = hiltViewModel()

            NewEditEmployeeScreen(
                employeeId = employeeId,
                onSave = {
                    navController.popBackStack()
                },
                onCancel = {
                    navController.navigateUp()
                },
                viewModel = viewModel
            )
        }

        // Agrega aquí el resto de pantallas cuando las crees
        composable(Screen.Clients.route) {
            // ClientsScreen()
            Text("Pantalla de Clientes")
        }

        composable(Screen.Services.route) {
            // ServicesScreen()
            Text("Pantalla de Servicios")
        }

        // ... y así con las demás
    }
}