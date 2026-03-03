package com.proyecto.PeluPos.ui.features.servicios

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Empleado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewServicioScreen(
    empleadosDisponibles: List<Empleado>, // Necesitamos la lista para el desplegable
    onNavigateBack: () -> Unit,
    onSaveServicio: (nombre: String, precio: Double, descripcion: String, empleado: Empleado) -> Unit
) {
    // ----------------------------------------------------
    // ESTADO DEL FORMULARIO
    // ----------------------------------------------------
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Estado para el Dropdown de Empleados
    var empleadoSeleccionado by remember { mutableStateOf<Empleado?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    // ----------------------------------------------------
    // DISEÑO
    // ----------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- 1. Título ---
        Text(
            text = "Nuevo Servicio",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- 2. Campo: Nombre del Servicio ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del servicio") },
            placeholder = { Text("Ej. Corte Degradado") },
            leadingIcon = {
                Icon(Icons.Default.Star, contentDescription = "Icono Servicio")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. Campo: Precio ---
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio (€)") },
            placeholder = { Text("0.00") },
            leadingIcon = {
                Icon(Icons.Default.AttachMoney, contentDescription = "Icono Precio")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 4. Dropdown: Selección de Empleado ---
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            OutlinedTextField(
                // Mostramos el nombre si hay uno seleccionado, o vacío
                value = empleadoSeleccionado?.nombre ?: "",
                onValueChange = {},
                readOnly = true, // El usuario no escribe aquí, solo selecciona
                label = { Text("Empleado Asignado") },
                placeholder = { Text("Selecciona un empleado") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Icono Empleado")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), // Necesario para que el menú se ancle a este campo
                shape = RoundedCornerShape(12.dp),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                empleadosDisponibles.forEach { empleado ->
                    DropdownMenuItem(
                        text = { Text(text = empleado.nombre) },
                        onClick = {
                            empleadoSeleccionado = empleado
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 5. Campo: Descripción (Multilínea) ---
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            placeholder = { Text("Detalles del servicio...") },
            leadingIcon = {
                Icon(Icons.Default.Description, contentDescription = "Icono Descripción")
            },
            minLines = 3, // Hace el campo más alto por defecto
            maxLines = 5,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón hacia abajo

        // --- 6. Botón de Guardar ---
        Button(
            onClick = {
                val precioDouble = precio.toDoubleOrNull() ?: 0.0
                if (nombre.isNotBlank() && empleadoSeleccionado != null) {
                    onSaveServicio(nombre, precioDouble, descripcion, empleadoSeleccionado!!)
                    onNavigateBack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            // Solo habilitamos el botón si hay nombre y se ha seleccionado un empleado
            enabled = nombre.isNotBlank() && empleadoSeleccionado != null
        ) {
            Text(
                text = "Guardar Servicio",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
@Preview(showBackground = true, name = "Nuevo Servicio")
@Composable
fun NewServicioScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Simulamos unos empleados para que la Preview tenga datos en el desplegable
            val empleadosMock = listOf(
                Empleado(1L, nombre = "Carlos", telefono = 63283939, email = "carlos@gmail.com", cargo = "gerente"),
                Empleado(2L, nombre = "Elena",telefono = 63283939, email = "carlos@gmail.com", cargo = "gerente"),
                Empleado(3L, nombre ="Lucía", telefono = 63283939, email = "carlos@gmail.com", cargo = "gerente")
            )

            NewServicioScreen(
                empleadosDisponibles = empleadosMock,
                onNavigateBack = {},
                onSaveServicio = { _, _, _, _ -> }
            )
        }
    }
}