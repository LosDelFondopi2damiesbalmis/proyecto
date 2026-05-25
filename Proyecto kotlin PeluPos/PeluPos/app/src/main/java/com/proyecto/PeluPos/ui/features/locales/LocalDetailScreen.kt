package com.proyecto.PeluPos.ui.features.locales
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalDetailScreen(
    local: Local?,
    onBack: () -> Unit,
    onEditClick: () -> Unit
) {
    if (local == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Local no encontrado", color = MaterialTheme.colorScheme.error)
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) { Text("Volver") }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Local", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    IconButton(onClick = onEditClick) { Icon(Icons.Default.Edit, "Editar Local") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // --- 1. CABECERA DEL LOCAL ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.1f),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = local.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = local.direccion,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            // --- 2. LISTA DE EMPLEADOS ---
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Equipo Asignado (${local.empleadoCollection.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                if (local.empleadoCollection.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "No hay empleados asignados a este local todavía.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                } else {
                    local.empleadoCollection.forEach { empleado ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    modifier = Modifier.size(46.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = empleado.nombre.take(1).uppercase(),
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = empleado.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(text = empleado.cargo, fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium)

                                    if (empleado.telefono != 0L) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "📞 ${empleado.telefono}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
// ==========================================
// MOCKS PARA LAS PREVIEWS
// ==========================================
private val empleadoMock1 = Empleado(
    idEmpleado = 1L,
    telefono = 600123456L,
    email = "carlos@pelupos.com",
    cargo = "Barbero",
    nombre = "Carlos Ruiz",
    idLocal = null
)

private val empleadoMock2 = Empleado(
    idEmpleado = 2L,
    telefono = 611222333L,
    email = "elena@pelupos.com",
    cargo = "Estilista",
    nombre = "Elena Gómez",
    idLocal = null
)

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Detalle Local (Con equipo)")
@Composable
fun LocalDetailScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LocalDetailScreen(
                local = Local(
                    idLocal = 1L,
                    nombre = "Barbería Centro",
                    direccion = "Av. de la Constitución 45, Madrid",
                    empleadoCollection = mutableListOf(empleadoMock1, empleadoMock2)
                ),
                onBack = {},
                onEditClick = {}
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5", name = "2. Detalle Local (Vacío)")
@Composable
fun LocalDetailScreenEmptyPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LocalDetailScreen(
                local = Local(
                    idLocal = 2L,
                    nombre = "Local Norte (En obras)",
                    direccion = "C/ Gran Vía 12, Bilbao",
                    empleadoCollection = mutableListOf() // Lista vacía
                ),
                onBack = {},
                onEditClick = {}
            )
        }
    }
}