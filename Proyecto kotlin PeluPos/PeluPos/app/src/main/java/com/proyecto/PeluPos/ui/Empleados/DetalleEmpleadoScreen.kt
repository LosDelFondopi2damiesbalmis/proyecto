package com.proyecto.PeluPos.ui.Empleados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEmpleadoScreen(
    empleadoId: Long,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    viewModel: EmpleadoViewModel = hiltViewModel()
) {
    val empleado by viewModel.selectedEmployee.collectAsState()

    LaunchedEffect(empleadoId) {
        viewModel.loadEmployee(empleadoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle Empleado") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = {
                        viewModel.deleteEmployee(empleadoId)
                        onBack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (empleado == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            empleado?.let { emp ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        // Header con avatar
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(120.dp),
                                shape = CircleShape,
                                color = if (emp.activo) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.error
                                }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${emp.nombre.first()}${emp.apellidos.first()}",
                                        style = MaterialTheme.typography.displayLarge,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "${emp.nombre} ${emp.apellidos}",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Estado - SIN BADGE, usando Chip
                            AssistChip(
                                onClick = {},
                                label = {
                                    Text(if (emp.activo) "ACTIVO" else "INACTIVO")
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (emp.activo) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.errorContainer
                                    },
                                    labelColor = if (emp.activo) {
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.onErrorContainer
                                    }
                                )
                            )
                        }
                    }

                    // Información Personal
                    item {
                        Card(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Información Personal",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                InfoRow(
                                    icon = Icons.Default.Person,
                                    title = "Nombre completo",
                                    value = "${emp.nombre} ${emp.apellidos}"
                                )

                                InfoRow(
                                    icon = Icons.Default.Badge,
                                    title = "DNI",
                                    value = emp.dni
                                )

                                InfoRow(
                                    icon = Icons.Default.Phone,
                                    title = "Teléfono",
                                    value = emp.telefono
                                )

                                InfoRow(
                                    icon = Icons.Default.Email,
                                    title = "Email",
                                    value = emp.email
                                )

                                InfoRow(
                                    icon = Icons.Default.DateRange,
                                    title = "Fecha de contratación",
                                    value = SimpleDateFormat("dd/MM/yyyy").format(emp.fechaContratacion)
                                )

                                InfoRow(
                                    icon = Icons.Default.Work,
                                    title = "Tipo de contrato",
                                    value = emp.tipoContrato
                                )

                                InfoRow(
                                    icon = Icons.Default.AttachMoney,
                                    title = "Salario",
                                    value = "%.2f€".format(emp.salario)
                                )
                            }
                        }
                    }

                    // Especialidades
                    item {
                        Card(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Especialidades",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )

                                // Especialidades en columnas
                                Column {
                                    emp.especialidades.forEach { especialidad ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = especialidad,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Notas
                    if (emp.notas.isNotBlank()) {
                        item {
                            Card(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Notas",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )

                                    Text(
                                        text = emp.notas,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}