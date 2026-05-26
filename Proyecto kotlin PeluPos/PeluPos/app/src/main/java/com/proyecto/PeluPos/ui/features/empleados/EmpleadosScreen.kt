package com.proyecto.PeluPos.ui.features.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.proyecto.PeluPos.models.Empleado

import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadosScreen(
    state: EmpleadosUiState, // Recibe el estado
    onEvent: (EmpleadosEvent) -> Unit,
    toggleSidebar: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Long) -> Unit, // 🚨 ¡Aquí está el cambio! (Long)
    onNavigateToStats: (Long) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // ON_RESUME significa "La pantalla acaba de aparecer frente al usuario"
            if (event == Lifecycle.Event.ON_RESUME) {
                // Llama al evento que recarga los datos desde tu base de datos
                onEvent(EmpleadosEvent.CargarDatos)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Empleados", fontWeight = FontWeight.Bold) },
                navigationIcon = {})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(EmpleadosEvent.PrepararNuevoEmpleado)
                onNavigateToCreate()
            }) {
                Icon(Icons.Default.Add, "Crear Empleado")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            items(state.empleados) { empleado ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    // 🚀 LA MAGIA RESPONSIVE:
                    BoxWithConstraints {
                        val isCompact = this.maxWidth < 300.dp // Si el menú lateral roba mucho espacio

                        if (isCompact) {
                            // --- DISEÑO VERTICAL (Menú lateral abierto) ---
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Avatar más pequeño
                                    Surface(shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(40.dp)) {
                                        Icon(Icons.Default.Person, null, modifier = Modifier.padding(8.dp))
                                    }

                                    // Agrupamos los dos botones arriba a la derecha
                                    Row {
                                        IconButton(onClick = { onNavigateToStats(empleado.idEmpleado) }) {
                                            Icon(Icons.Default.BarChart, "Estadística", tint = MaterialTheme.colorScheme.primary)
                                        }
                                        IconButton(onClick = {
                                            onEvent(EmpleadosEvent.PrepararEdicion(empleado.idEmpleado))
                                            onNavigateToEdit(empleado.idEmpleado)
                                        }) {
                                            Icon(Icons.Default.Edit, "Modificar")
                                        }
                                    }
                                }

                                // Textos protegidos contra saltos de línea
                                Text(empleado.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(empleado.cargo, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text(empleado.email, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)

                                if (empleado.idLocal != null) {
                                    Text("📍 ${empleado.idLocal!!.nombre}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        } else {
                            // --- DISEÑO HORIZONTAL ORIGINAL (Pantalla normal) ---
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(shape = MaterialTheme.shapes.extraLarge, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(50.dp)) {
                                    Icon(Icons.Default.Person, null, modifier = Modifier.padding(12.dp))
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(empleado.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(empleado.cargo, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(empleado.email, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    if (empleado.idLocal != null) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("📍 ${empleado.idLocal!!.nombre}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                                IconButton(onClick = { onNavigateToStats(empleado.idEmpleado) }) {
                                    Icon(Icons.Default.BarChart, "Estadística", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = {
                                    onEvent(EmpleadosEvent.PrepararEdicion(empleado.idEmpleado))
                                    onNavigateToEdit(empleado.idEmpleado)
                                }) {
                                    Icon(Icons.Default.Edit, "Modificar")
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}
private val mockLocal1 = Local(1L, "Sede Central", "Calle Mayor, 10")
private val mockLocal2 = Local(2L, "Sucursal Norte", "Avenida Libertad, 45")
private val mockLocales = listOf(mockLocal1, mockLocal2)

private val mockEmpleados = listOf(
    Empleado(1L, 600123456L, "laura@pelupos.com", "Estilista Principal", "Laura Gómez", mockLocal1),
    Empleado(2L, 611987654L, "carlos@pelupos.com", "Barbero", "Carlos Ruiz", mockLocal1),
    Empleado(3L, 622345678L, "marta@pelupos.com", "Colorista", "Marta Pérez", mockLocal2)
)

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista de Empleados")
@Composable
fun EmpleadosScreenPreview() {
    MaterialTheme {
        Surface {
            EmpleadosScreen(
                state = EmpleadosUiState(empleados = mockEmpleados),
                onEvent = {},
                toggleSidebar = {},
                onNavigateToCreate = {},
                onNavigateToEdit = {},
                onNavigateToStats = {}
            )
        }
    }
}

