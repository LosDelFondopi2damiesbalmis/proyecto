package com.proyecto.PeluPos.ui.features.empleados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
    empleadoId: Long? = null,
    localesDisponibles: List<Local>, // Lista para el desplegable
    onBackClick: () -> Unit,
    onSaveClick: (Empleado) -> Unit // Devolvemos el empleado creado/editado
) {
    val isEditing = empleadoId != null

    // En un caso real, si isEditing es true, aquí buscarías los datos del empleado.
    // Para simplificar la UI, inicializamos vacío.
    var nombre by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    // Estado para el selector de Local
    var localSeleccionado by remember { mutableStateOf<Local?>(null) }
    var expandedDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Modificar Empleado" else "Crear Empleado", fontWeight = FontWeight.Bold) },
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
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre y Apellidos") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cargo,
                onValueChange = { cargo = it },
                label = { Text("Cargo (Ej: Barbero, Estilista)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { if (it.all { char -> char.isDigit() }) telefono = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de Local (Dropdown Material 3)
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown }
            ) {
                OutlinedTextField(
                    value = localSeleccionado?.nombre ?: "Sin asignar",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Asignar Local") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Sin asignar") },
                        onClick = {
                            localSeleccionado = null
                            expandedDropdown = false
                        }
                    )
                    localesDisponibles.forEach { local ->
                        DropdownMenuItem(
                            text = { Text(local.nombre) },
                            onClick = {
                                localSeleccionado = local
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val nuevoEmpleado = Empleado(
                        idEmpleado = empleadoId ?: 0L, // 0 si es nuevo, o generarlo en BD
                        telefono = telefono.toLongOrNull() ?: 0L,
                        email = email,
                        cargo = cargo,
                        nombre = nombre,
                        local = localSeleccionado
                    )
                    onSaveClick(nuevoEmpleado)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
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
                empleadoId = null,
                localesDisponibles = mockLocales,
                onBackClick = {},
                onSaveClick = {}
            )
        }
    }
}
@Preview(showBackground = true, device = "id:pixel_5", name = "3. Editar Empleado")
@Composable
fun EmpleadoFormScreenEditPreview() {
    MaterialTheme {
        Surface {
            // Simulamos que le pasamos un ID existente (ej: 1L)
            EmpleadoFormScreen(
                empleadoId = 1L,
                localesDisponibles = mockLocales,
                onBackClick = {},
                onSaveClick = {}
            )
        }
    }
}