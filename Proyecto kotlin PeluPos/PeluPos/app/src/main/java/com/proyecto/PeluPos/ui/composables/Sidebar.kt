package com.proyecto.PeluPos.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import com.proyecto.PeluPos.ui.theme.SidebarColors

@Composable
fun Sidebar(isSidebarVisible : Boolean)
{
    

    // 2. Definir el ancho de la barra:
    // Ancho si está visible (150.dp) o si está oculto/colapsado (0.dp o un ícono pequeño, aquí usaremos 0.dp para simplicidad)
    val sidebarWidth = if (isSidebarVisible) 150.dp else 0.dp

    // Para una transición más suave, podemos animar el ancho:
    val animatedWidth by animateDpAsState(
        targetValue = sidebarWidth,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
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
            // ... resto de botones
        }

        // Footer Usuario
        Column(modifier = Modifier.padding(top = 20.dp)) {
            NavSeparator()
            Text("Usuario: Admin", color = SidebarColors.ContentSecondary, fontSize = 12.sp)
            Text("Peluquería Central", color = SidebarColors.ContentSecondary, fontSize = 12.sp)
        }
    }
}
@Preview
@Composable
fun SideBarPreview()
{
    var isSidebarVisible by remember { mutableStateOf(true) }
    PeluPosTheme {
        Sidebar(isSidebarVisible)
    }
}