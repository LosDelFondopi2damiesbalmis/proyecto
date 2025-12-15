// ui/composables/Sidebar.kt (VERSIÓN CORREGIDA)
package com.proyecto.PeluPos.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.proyecto.PeluPos.ui.navigation.Screen
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import com.proyecto.PeluPos.ui.theme.SidebarColors

@Composable
fun Sidebar(
    isSidebarVisible: Boolean,
    currentRoute: String? = null,
    onNavigationItemClick: (String) -> Unit = {},
    onToggleSidebar: () -> Unit = {}  // ← NO puedes modificar parámetros aquí
) {
    // PROBLEMA 1: Cuando isSidebarVisible = false, width = 0.dp (desaparece)
    // SOLUCIÓN: Usar width mínimo cuando esté colapsada
    val sidebarWidth = if (isSidebarVisible) 150.dp else 60.dp  // ← Cambiado de 0.dp a 60.dp

    val animatedWidth by animateDpAsState(
        targetValue = sidebarWidth,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Column(
        modifier = Modifier
            .width(animatedWidth)
            .fillMaxHeight()
            .background(SidebarColors.Background)
            .padding(10.dp)
    ) {
        // Logo y botón de toggle
        Column(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {
            if (isSidebarVisible) {
                // Cuando está visible, mostrar logo completo
                Text(
                    "PeluPOS",
                    color = SidebarColors.Content,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Gestión de peluquerías",
                    color = SidebarColors.ContentSecondary,
                    fontSize = 12.sp
                )
            } else {
                // Cuando está colapsada, mostrar solo inicial
                Text(
                    "P",
                    color = SidebarColors.Content,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón de toggle - CORREGIDO
            IconButton(
                onClick = onToggleSidebar,  // ← Usa el callback que viene de MainScreen
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = if (isSidebarVisible) Icons.Default.ArrowBack else Icons.Default.ArrowForward,
                    contentDescription = "Alternar barra lateral",
                    tint = SidebarColors.Content
                )
            }
        }

        // Menú de navegación
        Column(modifier = Modifier.weight(1f)) {
            // Dashboard
            NavButton(
                text = "Dashboard",
                onClick = { onNavigationItemClick(Screen.Dashboard.route) },
                isActive = currentRoute == Screen.Dashboard.route,
                isExpanded = isSidebarVisible  // ← Nueva propiedad
            )

            NavSeparator(isVisible = isSidebarVisible)

            // Clientes
            NavButton(
                text = "Clientes",
                onClick = { onNavigationItemClick(Screen.Clients.route) },
                isActive = currentRoute == Screen.Clients.route,
                isExpanded = isSidebarVisible
            )

            // Servicios
            NavButton(
                text = "Servicios",
                onClick = { onNavigationItemClick(Screen.Services.route) },
                isActive = currentRoute == Screen.Services.route,
                isExpanded = isSidebarVisible
            )

            // Productos
            NavButton(
                text = "Productos",
                onClick = { onNavigationItemClick(Screen.Products.route) },
                isActive = currentRoute == Screen.Products.route ||
                        currentRoute?.startsWith("product_detail") == true ||
                        currentRoute?.startsWith("edit_product") == true ||
                        currentRoute == "new_product",
                isExpanded = isSidebarVisible
            )

            // Empleados
            NavButton(
                text = "Empleados",
                onClick = { onNavigationItemClick(Screen.Employees.route) },
                isActive = currentRoute == Screen.Employees.route ||
                        currentRoute?.startsWith("employee_detail") == true ||
                        currentRoute == "new_employee",
                isExpanded = isSidebarVisible
            )

            // Ventas
            NavButton(
                text = "Ventas",
                onClick = { onNavigationItemClick(Screen.Sales.route) },
                isActive = currentRoute == Screen.Sales.route,
                isExpanded = isSidebarVisible
            )

            // Usuarios
            NavButton(
                text = "Usuarios",
                onClick = { onNavigationItemClick(Screen.Users.route) },
                isActive = currentRoute == Screen.Users.route,
                isExpanded = isSidebarVisible
            )

            // Locales
            NavButton(
                text = "Locales",
                onClick = { onNavigationItemClick(Screen.Locations.route) },
                isActive = currentRoute == Screen.Locations.route,
                isExpanded = isSidebarVisible
            )
        }

        // Footer Usuario
        Column(
            modifier = Modifier.padding(top = 20.dp),
            horizontalAlignment = if (isSidebarVisible) Alignment.Start else Alignment.CenterHorizontally
        ) {
            NavSeparator(isVisible = isSidebarVisible)

            if (isSidebarVisible) {
                Text(
                    "Usuario: Admin",
                    color = SidebarColors.ContentSecondary,
                    fontSize = 12.sp
                )
                Text(
                    "Peluquería Central",
                    color = SidebarColors.ContentSecondary,
                    fontSize = 12.sp
                )
            } else {
                // Cuando está colapsada, mostrar solo "A" (Admin)
                Text(
                    "A",
                    color = SidebarColors.ContentSecondary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// NavButton actualizado para manejar modo colapsado
@Composable
fun NavButton(
    text: String,
    onClick: () -> Unit,
    isActive: Boolean = false,
    isExpanded: Boolean = true
) {
    // Modo colapsado: mostrar solo la primera letra
    if (!isExpanded) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = text.first().toString(),
                color = if (isActive) SidebarColors.Content else SidebarColors.ContentSecondary,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                fontSize = 16.sp
            )
        }
    } else {
        // Modo expandido: mostrar texto completo
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isActive) SidebarColors.Hover else SidebarColors.Background,
                contentColor = if (isActive) SidebarColors.Content else SidebarColors.ContentSecondary
            ),
            shape = ButtonDefaults.elevatedShape
        ) {
            Text(
                text = text,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

// NavSeparator actualizado
@Composable
fun NavSeparator(isVisible: Boolean = true) {
    if (isVisible) {
        Divider(
            color = SidebarColors.Separator,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(name = "Sidebar Expandida")
@Composable
fun SidebarExpandedPreview() {
    PeluPosTheme {
        Sidebar(
            isSidebarVisible = true,
            currentRoute = Screen.Dashboard.route,
            onNavigationItemClick = {},
            onToggleSidebar = {}
        )
    }
}

@Preview(name = "Sidebar Colapsada")
@Composable
fun SidebarCollapsedPreview() {
    PeluPosTheme {
        Sidebar(
            isSidebarVisible = false,
            currentRoute = Screen.Dashboard.route,
            onNavigationItemClick = {},
            onToggleSidebar = {}
        )
    }
}