package com.proyecto.PeluPos.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.ui.composables.DashboardCard
import com.proyecto.PeluPos.ui.composables.DataRow
import com.proyecto.PeluPos.ui.composables.NavButton
import com.proyecto.PeluPos.ui.composables.NavSeparator
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import com.proyecto.PeluPos.ui.theme.SidebarColors

@Composable
fun MainScreen() {
    // 1. Definir el estado: Por defecto está visible (true)
    var isSidebarVisible by remember { mutableStateOf(true) }

    // 2. Definir el ancho de la barra:
    // Ancho si está visible (150.dp) o si está oculto/colapsado (0.dp o un ícono pequeño, aquí usaremos 0.dp para simplicidad)
    val sidebarWidth = if (isSidebarVisible) 150.dp else 0.dp

    // Para una transición más suave, podemos animar el ancho:
    val animatedWidth by animateDpAsState(
        targetValue = sidebarWidth,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    PeluPosTheme {
        Row(modifier = Modifier.fillMaxSize()) {

            // --- SIDEBAR (Siempre oscuro) ---
            Column(
                modifier = Modifier
                    .width(animatedWidth)
                    .fillMaxHeight()
                    .background(SidebarColors.Background) // Color fijo XAML #111827
                    .padding(10.dp)
            ) {
                // Logo
                Column(modifier = Modifier.padding(bottom = 20.dp)) {
                    Text("PeluPOS", color = SidebarColors.Content, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Gestión de peluquerías", color = SidebarColors.ContentSecondary, fontSize = 12.sp)
                }

                // Menú
                Column(modifier = Modifier.weight(1f)) {
                    NavButton("Dashboard", onClick = {}, isActive = true) // Ejemplo activo
                    NavSeparator()
                    NavButton("Clientes", onClick = {})
                    NavButton("Servicios", onClick = {})
                    NavButton("Productos", onClick = {})
                    NavButton("Empleado", onClick = {})
                    NavButton("Ventas", onClick = {})
                    NavButton("Usuarios", onClick = {})
                    NavButton("Locales", onClick = {})

                    // ... resto de botones
                }

                // Footer Usuario
                Column(modifier = Modifier.padding(top = 20.dp)) {
                    NavSeparator()
                    Text("Usuario: Admin", color = SidebarColors.ContentSecondary, fontSize = 12.sp)
                    Text("Peluquería Central", color = SidebarColors.ContentSecondary, fontSize = 12.sp)
                }
            }

            // --- CONTENIDO
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.background) // Color de fondo del tema
            ) {
                DashboardPage(toggleSidebar = { isSidebarVisible = !isSidebarVisible })
            }
        }
    }
}
@Composable
fun DashboardPage(toggleSidebar: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        // -- CABECERA PÁGINA --

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp) // Añadimos padding al contenido general
        ) {

            // -- CABECERA PÁGINA --
            Row( // Usamos Row para alinear el título y el botón
                modifier = Modifier.padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de alternancia (Toggle Button)
                IconButton(onClick = toggleSidebar) {
                    // Puedes usar un icono de "Menú" (si la barra está oculta) o "Flecha" (si está visible)
                    Icon(
                        imageVector = Icons.Default.Menu, // Usaremos solo Menú por simplicidad
                        contentDescription = "Alternar Barra Lateral",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Títulos (ahora en Column)
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = "Dashboard general",
                        // ... estilos
                    )
                    Text(
                        text = "Resumen de ventas, empleados y stock",
                        // ... estilos
                    )
                }
            }

            // -- GRID 2x2 --
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
                        DataRow(
                            "10:45",
                            "María Pérez",
                            "Peinado evento",
                            "35,00 €",
                            weights = weights
                        )
                        DataRow(
                            "11:05",
                            "Juan Ruiz",
                            "Corte caballero",
                            "18,00 €",
                            weights = weights
                        )
                    }
                }

                // 🟦 TARJETA 2: EMPLEADOS TOP
                item {
                    DashboardCard(title = "Empleados con más ventas") {
                        val weights = listOf(0.5f, 0.2f, 0.3f) // 3 columnas
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
fun MainScreenPreview()
{
    PeluPosTheme {
        MainScreen()
    }
}