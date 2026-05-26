package com.proyecto.PeluPos.ui.features.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoStatsScreen(
    empleadoId: Long,
    onBackClick: () -> Unit,
    // El ViewModel de la lista general para sacar el nombre del empleado
    empleadosViewModel: EmpleadosViewModel = hiltViewModel(),
    // 🚀 NUESTRO NUEVO VIEWMODEL DE ESTADÍSTICAS
    statsViewModel: EmpleadoStatsViewModel = hiltViewModel()
) {
    val empleadosState by empleadosViewModel.uiState.collectAsStateWithLifecycle()
    val empleado = empleadosState.empleados.find { it.idEmpleado == empleadoId }

    // El estado con los cálculos matemáticos de Kotlin
    val statsState by statsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(empleadoId) {
        // Lanzamos el cálculo en Kotlin
        statsViewModel.cargarEstadisticasDesdeKotlin(empleadoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rendimiento del Mes", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Volver") }
                }
            )
        }
    ) { paddingValues ->
        if (empleado != null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(empleado.nombre, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Cargo: ${empleado.cargo}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text("Estadísticas del mes actual", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                if (statsState.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (statsState.error != null) {
                    Text(statsState.error!!, color = MaterialTheme.colorScheme.error)
                } else {
                    // 🚀 AQUÍ PONEMOS LOS RESULTADOS CALCULADOS EN KOTLIN
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        MetricCard(title = "Productos", value = statsState.productosVendidos.toString(), modifier = Modifier.weight(1f))
                        MetricCard(title = "Servicios", value = statsState.serviciosRealizados.toString(), modifier = Modifier.weight(1f))
                    }
                    MetricCard(
                        title = "Total Facturado",
                        value = String.format("%.2f €", statsState.totalFacturado),
                        modifier = Modifier.fillMaxWidth(),
                        isPrimary = true
                    )
                }
            }
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