// ui/composables/Sidebar.kt (VERSIÓN CORREGIDA)
package com.proyecto.PeluPos.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.proyecto.PeluPos.navigation.ClientesListRoute
import com.proyecto.PeluPos.navigation.DashboardRoute
import com.proyecto.PeluPos.navigation.EmpleadosListRoute
import com.proyecto.PeluPos.navigation.LocalesListRoute
import com.proyecto.PeluPos.navigation.ProductosListRoute
import com.proyecto.PeluPos.navigation.ServiciosListRoute
import com.proyecto.PeluPos.navigation.TpvHomeRoute
import com.proyecto.PeluPos.navigation.UsuariosListRoute

@Composable
fun Sidebar(
    isSidebarVisible: Boolean,
    currentDestination: NavDestination?, // ¡Recibe la ruta segura actual!
    usuarioNombre: String,
    usuarioRol: String,
    onNavigationItemClick: (Any) -> Unit, // Navega usando objetos de ruta
    onToggleSidebar: () -> Unit,
    onLogout: () -> Unit // Mini botón para salir
) {
    // Animación suave para el ancho de la barra
    val sidebarWidth by animateDpAsState(
        targetValue = if (isSidebarVisible) 180.dp else 60.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "sidebarWidth"
    )

    Column(
        modifier = Modifier
            .width(sidebarWidth)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(10.dp)
    ) {
        // ==========================================
        // 1. LOGO Y BOTÓN DE COLAPSAR
        // ==========================================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (isSidebarVisible) {
                Text(
                    "PeluPOS",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Gestión TPV",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    "P",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = onToggleSidebar, modifier = Modifier.padding(top = 8.dp)) {
                Icon(
                    imageVector = if (isSidebarVisible) Icons.Default.MenuOpen else Icons.Default.Menu,
                    contentDescription = "Alternar barra",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // ==========================================
        // 2. MENÚ DE NAVEGACIÓN
        // ==========================================
        Column(modifier = Modifier.weight(1f)) {

            // Función Helper para crear botones inteligentes
            @Composable
            fun MenuButton(
                text: String,
                routeObj: Any,
                icon: androidx.compose.ui.graphics.vector.ImageVector
            ) {
                // Magia: Comprueba si la ruta actual es la misma que la del botón
                val isActive = currentDestination?.hasRoute(routeObj::class) == true

                if (!isSidebarVisible) {
                    // MODO COLAPSADO (Solo Icono)
                    IconButton(
                        onClick = { onNavigationItemClick(routeObj) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            icon,
                            text,
                            tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    // MODO EXPANDIDO (Icono + Texto)
                    Button(
                        onClick = { onNavigationItemClick(routeObj) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isActive) MaterialTheme.colorScheme.primaryContainer else androidx.compose.ui.graphics.Color.Transparent,
                            contentColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        elevation = null
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text,
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Tus botones (Asegúrate de que estas rutas existen en tu archivo de Rutas)
            MenuButton("Dashboard", DashboardRoute, Icons.Default.Dashboard)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            MenuButton("TPV", TpvHomeRoute, Icons.Default.PointOfSale)
            MenuButton("Clientes", ClientesListRoute, Icons.Default.People)
            MenuButton("Servicios", ServiciosListRoute, Icons.Default.ContentCut)
            MenuButton("Productos", ProductosListRoute, Icons.Default.Inventory)
            MenuButton("Usuarios", UsuariosListRoute, Icons.Default.ManageAccounts)
            MenuButton("Locales", LocalesListRoute, Icons.Default.Store)
            MenuButton("Empleados", EmpleadosListRoute, Icons.Default.Man)
        }

        // ==========================================
        // 3. FOOTER USUARIO Y CERRAR SESIÓN
        // ==========================================
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isSidebarVisible) Arrangement.SpaceBetween else Arrangement.Center
        ) {
            if (isSidebarVisible) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        usuarioNombre,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        usuarioRol,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }
            }

            // Mini Botón para Cerrar Sesión
            IconButton(onClick = onLogout) {
                Icon(
                    Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}