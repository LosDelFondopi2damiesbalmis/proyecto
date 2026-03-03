package com.proyecto.PeluPos.ui.empleados

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Empleado

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadosScreen(
    empleados: List<Empleado>, // Recibimos la lista de tu base de datos o ViewModel
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    onNavigateToStats: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Empleados", fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Crear Empleado")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            items(empleados) { empleado ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.extraLarge,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(12.dp))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = empleado.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = empleado.cargo, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            // Datos extra
                            Text(text = empleado.email, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            // Mostrar Local si tiene
                            if (empleado.local != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "📍 ${empleado.local!!.nombre}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        IconButton(onClick = { onNavigateToStats(empleado.idEmpleado) }) {
                            Icon(Icons.Default.BarChart, contentDescription = "Estadística", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onNavigateToEdit(empleado.idEmpleado) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Modificar")
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) } // Espacio para que el FAB no tape el último
        }
    }
}
private val mockLocal1 = Local(1L, "Sede Central", "Calle Mayor, 10")
private val mockLocal2 = Local(2L, "Sucursal Norte", "Avenida Libertad, 45")

private val mockEmpleados = listOf(
    Empleado(1L, 600123456L, "laura@pelupos.com", "Estilista Principal", "Laura Gómez", mockLocal1),
    Empleado(2L, 611987654L, "carlos@pelupos.com", "Barbero", "Carlos Ruiz", mockLocal1),
    Empleado(3L, 622345678L, "marta@pelupos.com", "Colorista", "Marta Pérez", mockLocal2)
)

private val mockLocales = listOf(mockLocal1, mockLocal2)

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista de Empleados")
@Composable
fun EmpleadosScreenPreview() {
    MaterialTheme {
        Surface {
            EmpleadosScreen(
                empleados = mockEmpleados,
                onNavigateToCreate = {},
                onNavigateToEdit = {},
                onNavigateToStats = {},
                onBackClick = {}
            )
        }
    }
}