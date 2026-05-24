package com.proyecto.PeluPos.ui.dashboard



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.proyecto.PeluPos.navigation.AppNavHost
import com.proyecto.PeluPos.navigation.DashboardRoute
import com.proyecto.PeluPos.navigation.LoginRoute
import com.proyecto.PeluPos.ui.composables.DashboardCard
import com.proyecto.PeluPos.ui.composables.DataRow
import com.proyecto.PeluPos.ui.composables.Sidebar

@Composable
fun MainScreen() {
    var isSidebarVisible by remember { mutableStateOf(true) }
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val vmDashboard = hiltViewModel<DashboardViewModel>()
    val dashboardState by vmDashboard.uiState.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


            val currentRouteName = currentDestination?.route ?: ""


            val hideSidebar = currentRouteName.contains("LoginRoute") ||
                    currentRouteName.contains("Form") ||
                    currentRouteName.contains("Detail") ||
                    currentRouteName.contains("Detalle") ||
                    currentRouteName.contains("AddSale") ||
                    currentRouteName.contains("Permissions") ||
                    currentRouteName.contains("Denied")


            if (!hideSidebar) {
                Sidebar(
                    isSidebarVisible = isSidebarVisible,
                    currentDestination = currentDestination,
                    usuarioNombre = dashboardState.nombreUsuarioLogeado,
                    usuarioRol = dashboardState.rolUsuarioLogeado,
                    onToggleSidebar = { isSidebarVisible = !isSidebarVisible },
                    onNavigationItemClick = { ruta ->
                        if(ruta == DashboardRoute)
                        {
                            vmDashboard.cargarDatos()
                        }
                        navController.navigate(ruta) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLogout = {
                        // 1. Limpiamos el disco y el estado del ViewModel Maestro
                        vmDashboard.cerrarSesion()

                        // 2. Navegamos
                        navController.navigate(LoginRoute) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // 2. TU NAVHOST
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                AppNavHost(
                    navController = navController,
                    toggleSidebar = { isSidebarVisible = !isSidebarVisible },
                    viewModel = vmDashboard
                )
            }
        }
    }
}


@Composable
fun DashboardPage(
    state: DashboardUiState,
    toggleSidebar: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // --- CABECERA ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text("Dashboard general", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(
                    "Hola, ${state.nombreUsuarioLogeado}. Resumen de tu negocio:",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // --- TARJETAS ---
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 280.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🟦 TARJETA 1: ÚLTIMAS VENTAS (Por ahora simuladas)
            item {
                DashboardCard(title = "Últimas ventas") {
                    val weights = listOf(0.3f, 0.4f, 0.3f)
                    DataRow("Hora", "Cliente", "Importe", isHeader = true, weights = weights)
                    state.ultimasVentas.forEach { venta ->
                        DataRow(venta.first, venta.second, venta.third, weights = weights)
                    }
                }
            }

            // 🟦 TARJETA 2: RESUMEN NEGOCIO (¡Datos Reales!)
            item {
                DashboardCard(title = "Mi Negocio") {
                    val weights = listOf(0.7f, 0.3f)
                    DataRow("Métrica", "Total", isHeader = true, weights = weights)
                    DataRow("Empleados Activos", state.totalEmpleados.toString(), weights = weights)
                    DataRow("Locales Operativos", state.totalLocales.toString(), weights = weights)
                }
            }

            // 🟦 TARJETA 3: STOCK BAJO (¡Datos Reales!)
            item {
                DashboardCard(title = "Productos con stock bajo") {
                    val weights = listOf(0.7f, 0.3f)
                    DataRow("Producto", "Stock", isHeader = true, weights = weights)

                    if (state.productosBajoStock.isEmpty()) {
                        Text(
                            "Todo el stock está correcto",
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(8.dp)
                        )
                    } else {
                        state.productosBajoStock.forEach { prod ->
                            // Si el stock es 0, lo ponemos en rojo
                            val textColor =
                                if (prod.stock == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            DataRow(
                                prod.nombre,
                                "${prod.stock} uds",
                                weights = weights,
                                textColor = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DataRow(
    vararg texts: String,
    isHeader: Boolean = false,
    weights: List<Float>,
    textColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        texts.forEachIndexed { index, text ->
            Text(
                text = text,
                modifier = Modifier.weight(weights[index]),
                fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
                fontSize = if (isHeader) 13.sp else 14.sp,
                color = if (isHeader) MaterialTheme.colorScheme.onSurfaceVariant else textColor
            )
        }
    }
}

