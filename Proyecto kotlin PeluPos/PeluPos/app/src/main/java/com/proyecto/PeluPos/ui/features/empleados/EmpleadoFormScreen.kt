package com.proyecto.PeluPos.ui.features.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadoFormScreen(
    state: EmpleadosUiState,
    onEvent: (EmpleadosEvent) -> Unit,
    onBackClick: () -> Unit
) {
    val isEditing = state.editandoEmpleadoId != null
    var expandedDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Modificar Empleado" else "Crear Empleado", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, "Volver") } },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = { onEvent(EmpleadosEvent.BorrarEmpleado); onBackClick() }) {
                            Icon(Icons.Default.Delete, "Borrar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.formNombre,
                onValueChange = { onEvent(EmpleadosEvent.OnNombreChange(it)) },
                label = { Text("Nombre y Apellidos") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.formCargo,
                onValueChange = { onEvent(EmpleadosEvent.OnCargoChange(it)) },
                label = { Text("Cargo (Ej: Barbero, Estilista)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.formEmail,
                onValueChange = { onEvent(EmpleadosEvent.OnEmailChange(it)) },
                label = { Text("Correo Electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.formTelefono,
                onValueChange = { if (it.all { char -> char.isDigit() }) onEvent(EmpleadosEvent.OnTelefonoChange(it)) },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de Local
            ExposedDropdownMenuBox(expanded = expandedDropdown, onExpandedChange = { expandedDropdown = !expandedDropdown }) {
                OutlinedTextField(
                    value = state.formLocalSeleccionado?.nombre ?: "Sin asignar",
                    onValueChange = {}, readOnly = true, label = { Text("Asignar Local") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedDropdown) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedDropdown, onDismissRequest = { expandedDropdown = false }) {
                    DropdownMenuItem(
                        text = { Text("Sin asignar") },
                        onClick = { onEvent(EmpleadosEvent.OnLocalChange(null)); expandedDropdown = false }
                    )
                    state.localesDisponibles.forEach { local ->
                        DropdownMenuItem(
                            text = { Text(local.nombre) },
                            onClick = { onEvent(EmpleadosEvent.OnLocalChange(local)); expandedDropdown = false }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onEvent(EmpleadosEvent.GuardarEmpleado); onBackClick() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = state.isFormValid
            ) {
                Text(if (isEditing) "Actualizar Empleado" else "Guardar Empleado")
            }
        }
    }
}

private val mockLocal1 = Local(1L, "Sede Central", "Calle Mayor, 10")
private val mockLocal2 = Local(2L, "Sucursal Norte", "Avenida Libertad, 45")
private val mockLocales = listOf(mockLocal1, mockLocal2)
@Preview(showBackground = true, device = "id:pixel_5", name = "2. Crear Empleado (Vacío)")
@Composable
fun EmpleadoFormScreenCreatePreview() {
    MaterialTheme {
        Surface {
            EmpleadoFormScreen(
                state = EmpleadosUiState(
                    localesDisponibles = mockLocales,
                    editandoEmpleadoId = null // Al ser null, la pantalla sabe que es modo "Crear"
                ),
                onEvent = {},
                onBackClick = {}
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5", name = "3. Editar Empleado (Lleno)")
@Composable
fun EmpleadoFormScreenEditPreview() {
    MaterialTheme {
        Surface {
            EmpleadoFormScreen(
                state = EmpleadosUiState(
                    localesDisponibles = mockLocales,
                    editandoEmpleadoId = 1L, // Simulamos que estamos editando
                    formNombre = "Laura Gómez",
                    formCargo = "Estilista Principal",
                    formEmail = "laura@pelupos.com",
                    formTelefono = "600123456",
                    formLocalSeleccionado = mockLocal1
                ),
                onEvent = {},
                onBackClick = {}
            )
        }
    }
}