package com.proyecto.PeluPos.ui.features.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    // 1. Inyectamos el ViewModel que ya tienes cargado con toda la lista
    viewModel: EmpleadosViewModel = hiltViewModel()
) {
    // 2. Observamos el estado.
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // 3. Buscamos al empleado en la lista que ya está en memoria
    val empleado = state.empleados.find { it.idEmpleado == empleadoId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rendimiento", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        // 4. Si el empleado existe, mostramos sus datos reales
        if (empleado != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Empleado: ${empleado.nombre}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Cargo: ${empleado.cargo}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Email: ${empleado.email}", color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Aquí podrías mostrar datos de tu modelo si los añades
                    MetricCard(title = "Local", value = empleado.local?.nombre ?: "Sin local", modifier = Modifier.weight(1f))
                    MetricCard(title = "ID Empleado", value = empleado.idEmpleado.toString(), modifier = Modifier.weight(1f))
                }

                MetricCard(
                    title = "Teléfono",
                    value = empleado.telefono.toString(),
                    modifier = Modifier.fillMaxWidth(),
                    isPrimary = true
                )
            }
        } else {
            // Estado de carga o error
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
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