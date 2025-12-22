package com.proyecto.PeluPos.ui.Empleados


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyecto.PeluPos.models.Empleado



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadosScreen(
    onAddEmpleado: () -> Unit,
    onEmpleadoClick: (Long) -> Unit,
    viewModel: EmpleadoViewModel = hiltViewModel()
) {
    val empleados by viewModel.empleados.collectAsState()
    val busqueda by viewModel.busqueda.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Empleados - T Acuareadas") },
                actions = {
                    // Contador de empleados activos
                    val activosCount = remember(empleados) {
                        empleados.count { it.activo }
                    }
                    Text(
                        text = "$activosCount/${empleados.size} activos",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEmpleado) {
                Icon(Icons.Default.Add, contentDescription = "Añadir empleado")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { viewModel.buscarEmpleados(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar por nombre, teléfono, especialidad...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                trailingIcon = {
                    if (busqueda.isNotBlank()) {
                        IconButton(onClick = { viewModel.buscarEmpleados("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar búsqueda")
                        }
                    }
                },
                singleLine = true
            )

            // Indicador de carga
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Lista de empleados
                if (empleados.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Sin empleados",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No hay empleados registrados",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (busqueda.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Intenta con otros términos de búsqueda",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(empleados, key = { it.id }) { empleado ->
                            EmpleadoCard(
                                empleado = empleado,
                                onClick = { onEmpleadoClick(empleado.id) },
                                onToggleActivo = { viewModel.toggleActivo(empleado.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmpleadoCard(
    empleado: Empleado,
    onClick: () -> Unit,
    onToggleActivo: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (!empleado.activo) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Avatar y nombre
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar con iniciales
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = if (empleado.activo) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.errorContainer
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${empleado.nombre.first()}${empleado.apellidos.first()}",
                                style = MaterialTheme.typography.titleMedium,
                                color = if (empleado.activo) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onErrorContainer
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${empleado.nombre} ${empleado.apellidos}",
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if (!empleado.activo) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                ) {
                                    Text("INACTIVO", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }

                        // Especialidades
                        if (empleado.especialidades.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = empleado.especialidades.joinToString(" • "),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Contacto
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = empleado.telefono,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = empleado.email,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // Estado y acciones
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Botón de activo/inactivo
                FilterChip(
                    selected = empleado.activo,
                    onClick = onToggleActivo,
                    label = {
                        Text(if (empleado.activo) "Activo" else "Inactivo")
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                )

                // Tipo de contrato
                Text(
                    text = empleado.tipoContrato,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Salario
                Text(
                    text = "%.2f€".format(empleado.salario),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}