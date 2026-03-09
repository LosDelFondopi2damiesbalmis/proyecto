package com.proyecto.PeluPos.ui.features.servicios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Empleado
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.proyecto.PeluPos.models.Servicio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioFormScreen(
    state: ServiciosUiState,
    onEvent: (ServiciosEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val isEditing = state.editandoServicioId != null
    var dropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Servicio" else "Nuevo Servicio", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            onEvent(ServiciosEvent.BorrarServicio)
                            onNavigateBack()
                        }) {
                            Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.formNombre,
                onValueChange = { onEvent(ServiciosEvent.OnNombreChange(it)) },
                label = { Text("Nombre del servicio") },
                leadingIcon = { Icon(Icons.Default.Star, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = state.formPrecio,
                onValueChange = { onEvent(ServiciosEvent.OnPrecioChange(it)) },
                label = { Text("Precio (€)") },
                leadingIcon = { Icon(Icons.Default.AttachMoney, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Dropdown de Empleados
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = !dropdownExpanded }
            ) {
                OutlinedTextField(
                    value = state.formEmpleadoSeleccionado?.nombre ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Empleado Asignado") },
                    leadingIcon = { Icon(Icons.Default.Person, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    state.empleadosDisponibles.forEach { empleado ->
                        DropdownMenuItem(
                            text = { Text(empleado.nombre) },
                            onClick = {
                                onEvent(ServiciosEvent.OnEmpleadoChange(empleado))
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.formDescripcion,
                onValueChange = { onEvent(ServiciosEvent.OnDescripcionChange(it)) },
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, null) },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onEvent(ServiciosEvent.GuardarServicio)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = state.isFormValid
            ) {
                Text(if (isEditing) "Actualizar Servicio" else "Guardar Servicio", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
private val mockEmpleado = Empleado(1L, 63283939L, "carlos@gmail.com", "Barbero", "Carlos", null)
private val mockServicios = listOf(
    Servicio(1L, "Corte Degradado", 15.0, "Corte moderno con acabado en navaja", mockEmpleado),
    Servicio(2L, "Tratamiento Keratina", 85.0, "Hidratación profunda", mockEmpleado)
)
@Preview(showBackground = true, device = "id:pixel_5", name = "2. Crear Servicio")
@Composable
fun ServicioFormScreenPreview() {
    MaterialTheme {
        ServicioFormScreen(
            state = ServiciosUiState(empleadosDisponibles = listOf(mockEmpleado)),
            onEvent = {}, onNavigateBack = {}
        )
    }
}