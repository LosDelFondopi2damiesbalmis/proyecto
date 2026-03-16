package com.proyecto.PeluPos.ui.features.locales

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LocalFormScreen(
    state: LocalesUiState,
    onEvent: (LocalesEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val isEditing = state.editandoLocalId != null
    var expandedEmpleados by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditing) "Editar Local" else "Nuevo Local",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    // MUESTRA LA PAPELERA SOLO SI ESTAMOS EDITANDO
                    if (isEditing) {
                        IconButton(onClick = {
                            onEvent(LocalesEvent.BorrarLocal)
                            onNavigateBack()
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                "Eliminar",
                                tint = MaterialTheme.colorScheme.error
                            )
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // MUESTRA EL TEXTO DEL ID SOLO SI ESTAMOS EDITANDO
            if (isEditing) {
                Text(
                    text = "Editando información del Local #${state.editandoLocalId}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedTextField(
                value = state.formNombre,
                onValueChange = { onEvent(LocalesEvent.OnNombreChange(it)) },
                label = { Text("Nombre del Local") },
                leadingIcon = { Icon(Icons.Default.Home, null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.formDireccion,
                onValueChange = { onEvent(LocalesEvent.OnDireccionChange(it)) },
                label = { Text("Dirección") },
                leadingIcon = { Icon(Icons.Default.Place, null) },
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                "Equipo Asignado",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Selector de Empleados
            ExposedDropdownMenuBox(
                expanded = expandedEmpleados,
                onExpandedChange = { expandedEmpleados = !expandedEmpleados }
            ) {
                OutlinedTextField(
                    value = "Añadir Empleado...",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEmpleados) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedEmpleados,
                    onDismissRequest = { expandedEmpleados = false }) {
                    state.empleadosDisponibles.forEach { empleado ->
                        if (!state.formEmpleadosSeleccionados.contains(empleado)) {
                            DropdownMenuItem(
                                text = { Text("${empleado.nombre} (${empleado.cargo})") },
                                onClick = {
                                    onEvent(LocalesEvent.OnAddEmpleado(empleado))
                                    expandedEmpleados = false
                                }
                            )
                        }
                    }
                }
            }

            // Chips de empleados
            if (state.formEmpleadosSeleccionados.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    state.formEmpleadosSeleccionados.forEach { empleado ->
                        InputChip(
                            selected = true,
                            onClick = { onEvent(LocalesEvent.OnRemoveEmpleado(empleado)) },
                            label = { Text(empleado.nombre) },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    "Quitar",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BOTÓN GUARDAR
            Button(
                onClick = {
                    onEvent(LocalesEvent.GuardarLocal)
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = state.isFormValid
            ) {
                Text(
                    if (isEditing) "Guardar Cambios" else "Crear Local",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // TU BOTÓN DE CANCELAR
            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = null
            ) {
                Text("Cancelar", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
private val mockEmpleado1 = Empleado(1L, 600123456L, "a@a.com", "Barbero", "Carlos", null)
private val mockEmpleado2 = Empleado(2L, 611222333L, "b@b.com", "Estilista", "Elena", null)

private val mockLocales = listOf(
    Local(1L, "Barbería Centro", "Av. Constitución 45", mutableListOf(mockEmpleado1, mockEmpleado2)),
    Local(2L, "Local Norte", "C/ Gran Vía 12", mutableListOf())
)
@Preview(showBackground = true, device = "id:pixel_5", name = "2. Formulario Local")
@Composable
fun LocalFormPreview() {
    MaterialTheme {
        LocalFormScreen(
            state = LocalesUiState(
                formNombre = "Barbería Sur",
                formDireccion = "Av. Andalucía",
                formEmpleadosSeleccionados = listOf(mockEmpleado1),
                empleadosDisponibles = listOf(mockEmpleado1, mockEmpleado2)
            ),
            onEvent = {}, onNavigateBack = {}
        )
    }
}