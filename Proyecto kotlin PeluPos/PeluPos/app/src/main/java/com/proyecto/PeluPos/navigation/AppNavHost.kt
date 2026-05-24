package com.proyecto.PeluPos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.proyecto.PeluPos.ui.dashboard.DashboardViewModel


@Composable
fun AppNavHost(toggleSidebar: () -> Unit,
               navController: NavHostController,
               viewModel : DashboardViewModel
) {
    val context = LocalContext.current
    val activity = context as? Activity
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        // ==========================================
        // GRAFO PRINCIPAL: DASHBOARD
        // ==========================================
        dashboardDestination(
            toggleSidebar = toggleSidebar,
            vm = viewModel
        )
        // ==========================================
        // GRAFO 0: LOGIN
        // ==========================================
        loginDestination(
            navigateToHome = {

                navController.navigate(DashboardRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            onExitApp = {
                activity?.finish()
            },
            navController = navController,
        )

        // ==========================================
        // GRAFO 1: TPV
        // ==========================================
        tpvDestination(
            toggleSidebar = toggleSidebar,
            navigateToSales = {
                navController.navigate(VentasRoute)
            },
            navigateToCreateInvoice = { _, _ ->

                navController.navigate(AddSaleRoute)
            }
        )

        salesDestination(
            toggleSidebar = toggleSidebar,
            navigateToNewSale = {
                // Al ser un 'object', no necesita ID
                navController.navigate(AddSaleRoute)
            },
            navigateToSaleDetail = { idFactura ->
                // Aquí pasamos el ID que manda la lista
                navController.navigate(DetalleVentaRoute(idFactura = idFactura))
            },
            onBack = {
                navController.popBackStack()
            }
        )
//
//        // ==========================================
//        // GRAFO 2: GESTIÓN DE USUARIOS
//        // ==========================================
        usuariosDestination(
            navigateToForm = { id -> // 👈 1. Añadimos la variable 'id'
                // 2. Le pasamos el ID a la ruta (null = crea, número = edita)
                navController.navigate(UsuarioFormRoute(idUsuario = id))
            },
            onBack = {
                navController.popBackStack()
            }
        )
//        // ==========================================
//        // GRAFO 3: GESTIÓN DE CLIENTES
//        // ==========================================
        clientesDestination(
            toggleSidebar = toggleSidebar,
            navigateToForm = { id -> // 👈 ¡Añadimos la variable 'id' aquí!
                // Navegamos pasando el ID.
                // Si 'id' es null -> Crea uno nuevo. Si tiene número -> Edita.
                navController.navigate(ClienteFormRoute(idCliente = id))
            },
            navigateToDetail = { idCliente ->
                navController.navigate(ClienteDetailRoute(idCliente = idCliente))
            },
            onBack = {
                // Vuelve a la pantalla anterior mágicamente destruyendo el formulario
                navController.popBackStack()
            }
        )
//        // ==========================================
//        // GRAFO: 4 GESTIÓN DE LOCALES
//        // ==========================================
        localesDestination(
            toggleSidebar = toggleSidebar,
            navigateToForm = { id ->
                navController.navigate(LocalFormRoute(idLocal = id))
            },
            onBack = {
                navController.popBackStack()
            },
            navigateToDetail = { idDelLocal ->
                navController.navigate(LocalDetailRoute(idLocal = idDelLocal))
            }
        )
//        // ==========================================
//        // GRAFO: 5 GESTIÓN DE INVENTARIO / PRODUCTOS
//        // ==========================================
        productosDestination(
            toggleSidebar = toggleSidebar,
            navigateToForm = { id ->

                // 2. Le pasamos el ID a la ruta
                navController.navigate(ProductoFormRoute(idProducto = id))
            },
            onBack = {
                navController.popBackStack()
            }
        )
//        // ==========================================
//        // GRAFO: 6 GESTIÓN DE SERVICIOS
//        // ==========================================
        serviciosDestination(
            toggleSidebar = toggleSidebar,
            navigateToForm = { id -> // 👈 Recibe el ID
                navController.navigate(ServicioFormRoute(idServicio = id))
            },
            onBack = {
                navController.popBackStack()
            }
        )
//        // ==========================================
//        // GRAFO 7: GESTIÓN DE EMPLEADOS
//        // ==========================================
        empleadosDestination(
            toggleSidebar = toggleSidebar,
            navigateToForm = { id -> // 👈 1. Añadimos la variable 'id'
                // 2. Le pasamos el ID a la ruta (null = crea, número = edita)
                navController.navigate(EmpleadoFormRoute(idEmpleado = id))
            },
            navigateToStats = { idEmpleado ->
                navController.navigate(EmpleadoStatsRoute(idEmpleado = idEmpleado))
            },
            onBack = {
                navController.popBackStack()
            }
        )

    }
}