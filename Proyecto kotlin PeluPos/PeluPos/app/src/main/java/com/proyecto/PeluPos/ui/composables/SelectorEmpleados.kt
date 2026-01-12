package com.proyecto.PeluPos.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorEmpleados(
    empleadosDisponibles: List<String>,
    onEmpleadoSeleccionado: (String) -> Unit, // Callback para devolver el dato
    modifier: Modifier = Modifier
) {
    // Estados internos del componente (solo le importan a él)
    var expanded by remember { mutableStateOf(false) }
    var textoActual by remember { mutableStateOf("") }

    Box(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = textoActual,
                onValueChange = {},
                readOnly = true, // No dejar escribir, solo seleccionar
                label = { Text("Añadir empleado") },
                placeholder = { Text("Selecciona uno...") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                empleadosDisponibles.forEach { empleado ->
                    DropdownMenuItem(
                        text = { Text(text = empleado) },
                        onClick = {
                            // 1. Avisamos al padre que se eligió a este empleado
                            onEmpleadoSeleccionado(empleado)
                            // 2. Cerramos el menú
                            expanded = false
                            // 3. (Opcional) Reseteamos el texto o lo dejamos vacío para el siguiente
                            textoActual = ""
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}