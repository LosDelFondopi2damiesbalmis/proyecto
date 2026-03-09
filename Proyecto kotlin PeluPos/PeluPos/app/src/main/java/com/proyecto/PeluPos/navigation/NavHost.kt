package com.proyecto.PeluPos.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.proyecto.PeluPos.ui.features.clientes.ClientesViewModel
import com.proyecto.PeluPos.ui.features.locales.LocalesViewModel
import com.proyecto.PeluPos.ui.features.products.ProductosViewModel
import com.proyecto.PeluPos.ui.features.tpv.TpvViewModel
import com.proyecto.PeluPos.ui.features.usuarios.UsuariosViewModel
import com.proyecto.PeluPos.ui.features.ventas.FacturacionViewModel


@Composable
fun NavHost(toggleSidebar: () -> Unit) {
    val navController = rememberNavController()
    val vmTpv = hiltViewModel<TpvViewModel>()
    val sharedViewModel = hiltViewModel<FacturacionViewModel>()
    val vmUsuarios = hiltViewModel<UsuariosViewModel>()
    val vmClientes = hiltViewModel<ClientesViewModel>()
    val vmLocales = hiltViewModel<LocalesViewModel>()
    val vmProductos = hiltViewModel<ProductosViewModel>()
    NavHost(
        navController = navController,
        startDestination = ProductosListRoute
    ) {
        // ==========================================
        // GRAFO 0: LOGIN
        // ==========================================
        loginDestination(
            navigateToHome = {

                navController.navigate(TpvHomeRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            onExitApp = {

            }
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
    }
}