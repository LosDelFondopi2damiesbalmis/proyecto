package com.proyecto.PeluPos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.proyecto.PeluPos.ui.dashboard.DashboardViewModel
import com.proyecto.PeluPos.ui.features.clientes.ClientesViewModel
import com.proyecto.PeluPos.ui.features.empleados.EmpleadosViewModel
import com.proyecto.PeluPos.ui.features.locales.LocalesViewModel
import com.proyecto.PeluPos.ui.features.products.ProductosViewModel
import com.proyecto.PeluPos.ui.features.servicios.ServiciosViewModel
import com.proyecto.PeluPos.ui.features.tpv.TpvViewModel
import com.proyecto.PeluPos.ui.features.usuarios.UsuariosViewModel
import com.proyecto.PeluPos.ui.features.ventas.FacturacionViewModel


@Composable
fun AppNavHost(toggleSidebar: () -> Unit,
               navController: NavHostController
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val vmTpv = hiltViewModel<TpvViewModel>()
    val sharedViewModel = hiltViewModel<FacturacionViewModel>()
    val vmUsuarios = hiltViewModel<UsuariosViewModel>()
    val vmClientes = hiltViewModel<ClientesViewModel>()
    val vmLocales = hiltViewModel<LocalesViewModel>()
    val vmProductos = hiltViewModel<ProductosViewModel>()
    val vmServicios = hiltViewModel<ServiciosViewModel>()
    val vmDashboard = hiltViewModel<DashboardViewModel>()
    val vmEmpleados = hiltViewModel<EmpleadosViewModel>()
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        // ==========================================
        // GRAFO PRINCIPAL: DASHBOARD
        // ==========================================
        dashboardDestination(
            vm = vmDashboard,
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

        // ==========================================
        // GRAFO 2: GESTIÓN DE USUARIOS
        // ==========================================
        usuariosDestination(
            vm = vmUsuarios,
            navigateToForm = {

                navController.navigate(UsuarioFormRoute)
            },
            onBack = {
                navController.popBackStack()
            }
        )
        // ==========================================
        // GRAFO 3: GESTIÓN DE CLIENTES
        // ==========================================
        clientesDestination(
            vm = vmClientes,
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
        // ==========================================
        // GRAFO: 4 GESTIÓN DE LOCALES
        // ==========================================
        localesDestination(
            vm = vmLocales,
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
        // ==========================================
        // GRAFO: 5 GESTIÓN DE INVENTARIO / PRODUCTOS
        // ==========================================
        productosDestination(
            vm = vmProductos,
            toggleSidebar = toggleSidebar,
            navigateToForm = {

                navController.navigate(ProductoFormRoute)
            },
            onBack = {

                navController.popBackStack()
            }
        )
        // ==========================================
        // GRAFO: 6 GESTIÓN DE SERVICIOS
        // ==========================================
        serviciosDestination(
            vm = vmServicios,
            toggleSidebar = toggleSidebar,
            navigateToForm = {
                navController.navigate(ServicioFormRoute)
            },
            onBack = {
                // Volvemos a la pantalla anterior
                navController.popBackStack()
            }
        )
        // ==========================================
        // GRAFO 7: GESTIÓN DE EMPLEADOS
        // ==========================================
        empleadosDestination(
            vm = vmEmpleados,
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