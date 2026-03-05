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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioFormScreen(
    usuarioId: Long? = null,
    empleadosDisponibles: List<Empleado>, // Necesitamos la lista de empleados de la BD
    onBackClick: () -> Unit,
    onSaveClick: (Usuario) -> Unit
) {
    val isEditing = usuarioId != null

    // Estados del formulario
    var nombreUsuario by remember { mutableStateOf("") } // El campo 'usuario'
    var contrasena by remember { mutableStateOf("") }

    // Estado para el Enum de Rol
    var rolSeleccionado by remember { mutableStateOf(RolUsuario.EMPLEADO) }
    var expandedRol by remember { mutableStateOf(false) }

    // Estado para vincular el Empleado
    var empleadoSeleccionado by remember { mutableStateOf<Empleado?>(null) }
    var expandedEmpleado by remember { mutableStateOf(false) }

    // Validación básica: necesitamos un empleado seleccionado y un nombre de usuario
    val isFormValid = empleadoSeleccionado != null && nombreUsuario.isNotBlank()

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
                    value = empleadoSeleccionado?.nombre ?: "Seleccionar Empleado...",
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Default.Badge, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEmpleado) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedEmpleado, onDismissRequest = { expandedEmpleado = false }) {
                    empleadosDisponibles.forEach { emp ->
                        DropdownMenuItem(
                            text = { Text(emp.nombre) },
                            onClick = {
                                empleadoSeleccionado = emp
                                expandedEmpleado = false
                            }
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // --- 2. DATOS DE ACCESO (LOGIN) ---
            Text("Datos de Acceso", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre de Usuario (Login)") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
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
                    value = rolSeleccionado.name, // Usamos el .name del Enum
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = { Icon(Icons.Default.AdminPanelSettings, null) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRol) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedRol, onDismissRequest = { expandedRol = false }) {
                    // Iteramos sobre todos los valores posibles de tu Enum
                    RolUsuario.values().forEach { rol ->
                        DropdownMenuItem(
                            text = { Text(rol.name) },
                            onClick = {
                                rolSeleccionado = rol
                                expandedRol = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (empleadoSeleccionado != null) {
                        val nuevoUsuario = Usuario(
                            idUsuario = usuarioId ?: 0L,
                            usuario = nombreUsuario,
                            contrasena = contrasena,
                            empleado = empleadoSeleccionado!!,
                            rolUsuario = rolSeleccionado
                        )
                        onSaveClick(nuevoUsuario)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = isFormValid // Deshabilitado si no hay empleado o nombre de usuario
            ) {
                Text(if (isEditing) "Actualizar Usuario" else "Guardar Usuario")
            }
        }
    }
}