package com.proyecto.PeluPos.ui.features.usuarios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.RolUsuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioFormScreen(
    state: UsuariosUiState, // Recibe el estado
    onEvent: (UsuariosEvent) -> Unit, // Recibe los eventos
    onBackClick: () -> Unit,
    onUsuarioGuardado: () -> Unit // Solo para hacer popBackStack
) {
    val isEditing = state.editandoUsuarioId != null

    // Estos estados de UI pura (abrir/cerrar dropdowns) se quedan aquí
    var expandedRol by remember { mutableStateOf(false) }
    var expandedEmpleado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Modificar Usuario" else "Crear Cuenta de Usuario", fontWeight = FontWeight.Bold) },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- 1. SELECCIONAR EMPLEADO ---
            Text("¿A qué empleado pertenece esta cuenta?", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            ExposedDropdownMenuBox(
                expanded = expandedEmpleado,
                onExpandedChange = { expandedEmpleado = !expandedEmpleado }
            ) {
                OutlinedTextField(
                    value = state.formEmpleadoSeleccionado?.nombre ?: "Seleccionar Empleado...",
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Default.Badge, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEmpleado) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedEmpleado, onDismissRequest = { expandedEmpleado = false }) {
                    state.empleadosDisponibles.forEach { emp ->
                        DropdownMenuItem(
                            text = { Text(emp.nombre) },
                            onClick = {
                                onEvent(UsuariosEvent.OnEmpleadoChange(emp)) // Evento al ViewModel
                                expandedEmpleado = false
                            }
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // --- 2. DATOS DE ACCESO ---
            Text("Datos de Acceso", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            OutlinedTextField(
                value = state.formNombreUsuario,
                onValueChange = { onEvent(UsuariosEvent.OnNombreUsuarioChange(it)) }, // Evento al ViewModel
                label = { Text("Nombre de Usuario (Login)") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.formContrasena,
                onValueChange = { onEvent(UsuariosEvent.OnContrasenaChange(it)) }, // Evento al ViewModel
                label = { Text(if (isEditing) "Nueva Contraseña (vacío para no cambiar)" else "Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            // --- 3. ROL DEL SISTEMA ---
            Text("Permisos", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            ExposedDropdownMenuBox(
                expanded = expandedRol,
                onExpandedChange = { expandedRol = !expandedRol }
            ) {
                OutlinedTextField(
                    value = state.formRol.name,
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Default.AdminPanelSettings, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRol) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedRol, onDismissRequest = { expandedRol = false }) {
                    RolUsuario.values().forEach { rol ->
                        DropdownMenuItem(
                            text = { Text(rol.name) },
                            onClick = {
                                onEvent(UsuariosEvent.OnRolChange(rol)) // Evento al ViewModel
                                expandedRol = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onEvent(UsuariosEvent.GuardarUsuario) // El ViewModel ya tiene todo para guardar
                    onUsuarioGuardado() // Volvemos atrás
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = state.isFormValid // Computado en el ViewModel
            ) {
                Text(if (isEditing) "Actualizar Usuario" else "Guardar Usuario")
            }
        }
    }
}