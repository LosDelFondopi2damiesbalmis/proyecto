package com.proyecto.PeluPos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


@Composable
fun AppNavHost(toggleSidebar: () -> Unit,
               navController: NavHostController
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
            toggleSidebar = toggleSidebar
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
                navController.navigate(AddSaleRoute)
            },
            navigateToSaleDetail = { idFactura ->

                navController.navigate(DetalleVentaRoute(idFactura))
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
            navigateToForm = {

                navController.navigate(UsuarioFormRoute)
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
            navigateToForm = {
                // Navegamos a la pantalla de crear/editar
                navController.navigate(ClienteFormRoute)
            },
            navigateToDetail = { idCliente ->
                // Si tienes o vas a crear una pantalla de detalle del cliente, la llamarías así:
                navController.navigate(ClienteDetailRoute(idCliente))

                // Si aún no la tienes, puedes dejar un println o un Toast por ahora:
                // println("Navegando al detalle del cliente: $idCliente")
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
            navigateToForm = {
                navController.navigate(LocalFormRoute)
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
            navigateToForm = {

                navController.navigate(ProductoFormRoute)
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
            navigateToForm = {
                navController.navigate(ServicioFormRoute)
            },
            onBack = {
                // Volvemos a la pantalla anterior
                navController.popBackStack()
            }
        )
//        // ==========================================
//        // GRAFO 7: GESTIÓN DE EMPLEADOS
//        // ==========================================
        empleadosDestination(
            toggleSidebar = toggleSidebar,
            navigateToForm = {
                navController.navigate(EmpleadoFormRoute)
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