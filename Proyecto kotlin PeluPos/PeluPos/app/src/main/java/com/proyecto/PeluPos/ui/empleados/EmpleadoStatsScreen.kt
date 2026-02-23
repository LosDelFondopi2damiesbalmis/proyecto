package com.proyecto.PeluPos.ui.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoStatsScreen(
    empleadoId: Long,
    onBackClick: () -> Unit
) {
    // Aquí buscarías el Empleado real usando el empleadoId.

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rendimiento", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Volver") }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Resumen de ventas", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Datos del mes actual", color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard(title = "Servicios realizados", value = "124", modifier = Modifier.weight(1f))
                MetricCard(title = "Productos vendidos", value = "18", modifier = Modifier.weight(1f))
            }

            MetricCard(
                title = "Total Facturado",
                value = "2.850,00 €",
                modifier = Modifier.fillMaxWidth(),
                isPrimary = true
            )
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier = Modifier, isPrimary: Boolean = false) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isPrimary) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 14.sp, color = if (isPrimary) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = if (isPrimary) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface)
        }
    }
}
private val mockLocal1 = Local(1L, "Sede Central", "Calle Mayor, 10")
private val mockLocal2 = Local(2L, "Sucursal Norte", "Avenida Libertad, 45")
private val mockLocales = listOf(mockLocal1, mockLocal2)
@Preview(showBackground = true, device = "id:pixel_5", name = "4. Estadísticas")
@Composable
fun EmpleadoStatsScreenPreview() {
    MaterialTheme {
        Surface {
            EmpleadoStatsScreen(
                empleadoId = 1L,
                onBackClick = {}
            )
        }
    }
}