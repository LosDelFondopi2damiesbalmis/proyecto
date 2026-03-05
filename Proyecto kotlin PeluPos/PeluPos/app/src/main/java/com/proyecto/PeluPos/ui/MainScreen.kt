package com.proyecto.PeluPos.ui

import ServicesScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio

import com.proyecto.PeluPos.ui.features.clientes.ClienteScreen
import com.proyecto.PeluPos.ui.composables.DashboardCard
import com.proyecto.PeluPos.ui.composables.DataRow
import com.proyecto.PeluPos.ui.composables.Sidebar
import com.proyecto.PeluPos.navigation.Screen
import com.proyecto.PeluPos.ui.features.locales.CreateLocalScreen
import com.proyecto.PeluPos.ui.features.locales.EditLocalScreen
import com.proyecto.PeluPos.ui.features.locales.LocalesScreen
import com.proyecto.PeluPos.ui.features.products.ProductsScreen
import com.proyecto.PeluPos.ui.features.tpv.TpvScreen
import com.proyecto.PeluPos.ui.theme.PeluPosTheme


@Composable
fun MainScreen() {
    var isSidebarVisible by remember { mutableStateOf(true) }
    val navController = rememberNavController()
    val products = remember { mutableStateListOf<Producto>() }
    val servicios = remember { mutableStateListOf<Servicio>() }
    PeluPosTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(
                isSidebarVisible = isSidebarVisible,
                currentRoute = navController.currentDestination?.route,
                onNavigationItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId ?: return@navigate) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onToggleSidebar = { isSidebarVisible = !isSidebarVisible }
            )

            // ESTO HAY QUE CAMBIARLO
            NavHost(
                navController = navController,
                startDestination = Screen.Dashboard.route,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // DASHBOARD
                composable(Screen.Dashboard.route) {
                    DashboardPage(
                        toggleSidebar = { isSidebarVisible = !isSidebarVisible }
                    )
                }

                // PRODUCTOS
                composable(Screen.Products.route) {
                    ProductsScreen(
                        toggleSidebar = { isSidebarVisible = !isSidebarVisible },
                        navigateToProductDetail = { productId ->
                            // Aquí puedes implementar la navegación al detalle
                        },
                        navigateToNewProduct = {
                            // ¡ESTA ES LA LÍNEA CLAVE!
                            navController.navigate(Screen.NewProduct.route)
                        }
                    )
                }

                // NUEVO PRODUCTO
//                composable(Screen.NewProduct.route) {
//                    NewProductScreen(
//                        onBackClick = { navController.popBackStack() },
//                        onSaveProduct = { newProduct ->
//                            // Agregar el nuevo producto a la lista
//                            products.add(newProduct)
//                            // Volver a la pantalla de productos
//                            navController.popBackStack()
//                        }
//                    )
//                }
                composable(Screen.Services.route) {
                    ServicesScreen(
                        toggleSidebar = { isSidebarVisible = !isSidebarVisible },
                        navigateToServiceDetail = { serviceId ->
                        },
                        navigateToNewService = {
                            navController.navigate(Screen.Services.route)
                        }
                    )
                }
                composable(Screen.Clients.route) {
                    ClienteScreen(
                        toggleSidebar = { isSidebarVisible = !isSidebarVisible },
                        navigateToClienteDetail = { clienteId ->
                        },
                        navigateToNewCliente = {
                            navController.navigate(Screen.Services.route)
                        }
                    )
                }
                composable(Screen.Locations.route) {
                    LocalesScreen(
                        toggleSidebar = { isSidebarVisible = !isSidebarVisible },
                        navigateToNewLocal = {
                            // Usamos la ruta del objeto Screen
                            navController.navigate(Screen.NewLocal.route)
                        },
                        navigateToLocalDetail = { localId ->
                            navController.navigate(Screen.EditLocal.createRoute(localId))
                        },
                        navigateToEditLocal = { localId ->
                            // Usamos la función helper para pasar el ID
                            navController.navigate(Screen.EditLocal.createRoute(localId))
                        }
                    )
                }

                // 2. CREAR NUEVO LOCAL
                composable(Screen.NewLocal.route) {
                    CreateLocalScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onSaveSuccess = { navController.popBackStack() }
                    )
                }

                // 3. EDITAR LOCAL (Recibiendo el ID)
                composable(
                    route = Screen.EditLocal.route,
                    arguments = listOf(navArgument("localId") { type = NavType.IntType })
                ) { backStackEntry ->
                    // Recuperamos el ID de los argumentos
                    val id = backStackEntry.arguments?.getInt("localId") ?: 0

                    EditLocalScreen(
                        localId = id,
                        onNavigateBack = { navController.popBackStack() },
                        onSaveSuccess = { navController.popBackStack() }
                    )
                }
//                composable(Screen.Tpv.route) {
//                    TpvScreen(
//                        toggleSidebar = { isSidebarVisible = !isSidebarVisible },
//                        navigateToSales = {
//                            // Esto cumple con la flecha del diagrama que va a "Muestra todas las Ventas"
//                            navController.navigate(Screen.Sales.route)
//                        },
//                        productosDisponibles = products,
//                        serviciosDisponibles = servicios,
//                        navigateToCreateInvoice = { _, _ -> }
//                    )
//                }
//                composable(Screen.Sales.route) {
//                    VentasScreen(
//                        toggleSidebar = { /* Lógica opcional */ },
//                        // 👇 AQUÍ CONECTAMOS EL BOTÓN "AÑADIR" CON LA PANTALLA
//                        navigateToNewSale = {
//                            navController.navigate(Screen.NewSale.route)
//                        },
//                        navigateToSaleDetail = { id ->
//                            navController.navigate(Screen.DetallesVentaScreen.createRoute(id))
//                        }
//                    )
//                }

                // AQUÍ PUEDES AÑADIR MÁS PANTALLAS:
                // composable(Screen.Clients.route) { ... }
                // composable(Screen.Services.route) { ... }
                // composable(Screen.Employees.route) { ... }
                // etc.
            }
        }
    }
}

@Composable
fun DashboardPage(toggleSidebar: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {



            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = "Dashboard general",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Resumen de ventas, empleados y stock",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 280.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // 🟦 TARJETA 1: ÚLTIMAS VENTAS
                item {
                    DashboardCard(title = "Últimas ventas") {
                        val weights = listOf(0.2f, 0.3f, 0.3f, 0.2f)
                        DataRow(
                            "Hora",
                            "Cliente",
                            "Servicio",
                            "Importe",
                            isHeader = true,
                            weights = weights
                        )
                        DataRow("10:15", "Ana López", "Corte + tinte", "45,00 €", weights = weights)
                        DataRow("10:45", "María Pérez", "Peinado evento", "35,00 €", weights = weights)
                        DataRow("11:05", "Juan Ruiz", "Corte caballero", "18,00 €", weights = weights)
                    }
                }

                // 🟦 TARJETA 2: EMPLEADOS TOP
                item {
                    DashboardCard(title = "Empleados con más ventas") {
                        val weights = listOf(0.5f, 0.2f, 0.3f)
                        DataRow(
                            "Empleado",
                            "Servicios",
                            "Total",
                            isHeader = true,
                            weights = weights
                        )
                        DataRow("Laura", "18", "420,00 €", weights = weights)
                        DataRow("Marta", "15", "380,00 €", weights = weights)
                        DataRow("Carlos", "12", "310,00 €", weights = weights)
                    }
                }

                // 🟦 TARJETA 3: VENTAS POR LOCAL
                item {
                    DashboardCard(title = "Ventas por local") {
                        val weights = listOf(0.4f, 0.3f, 0.3f)
                        DataRow("Local", "Hoy", "Este mes", isHeader = true, weights = weights)
                        DataRow("Centro Alicante", "12 vtas", "1.250 €", weights = weights)
                        DataRow("San Juan Playa", "8 vtas", "780 €", weights = weights)
                        DataRow("Elche Centro", "10 vtas", "1.050 €", weights = weights)
                    }
                }

                // 🟦 TARJETA 4: STOCK BAJO
                item {
                    DashboardCard(title = "Productos con stock bajo") {
                        val weights = listOf(0.6f, 0.2f, 0.2f)
                        DataRow("Producto", "Stock", "Min", isHeader = true, weights = weights)
                        DataRow("Champú teñido", "3", "10", weights = weights)
                        DataRow("Mascarilla hidra", "5", "12", weights = weights)
                        DataRow("Laca fijación", "2", "8", weights = weights)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    PeluPosTheme {
        MainScreen()
    }
}