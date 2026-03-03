package com.proyecto.PeluPos.ui.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.ui.theme.PeluPosTheme

@Composable
fun NewClienteScreen(
    onNavigateBack: () -> Unit,
    onSaveCliente: (nombre: String, telefono: Long, deuda: Double) -> Unit
) {
    // ----------------------------------------------------
    // ESTADO DEL FORMULARIO
    // ----------------------------------------------------
    // Guardamos los números como String en la UI para facilitar la escritura
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var deuda by remember { mutableStateOf("") }

    // ----------------------------------------------------
    // DISEÑO
    // ----------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- 1. Título de la Pantalla ---
        Text(
            text = "Nuevo Cliente",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- 2. Campo: Nombre ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            placeholder = { Text("Ej. Ana García Pérez") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Icono de Persona")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp) // Manteniendo tu estética de bordes
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. Campo: Teléfono ---
        OutlinedTextField(
            value = telefono,
            onValueChange = {
                // Filtramos para que solo pueda escribir números
                if (it.all { char -> char.isDigit() }) telefono = it
            },
            label = { Text("Teléfono") },
            placeholder = { Text("Ej. 600123456") },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = "Icono de Teléfono")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 4. Campo: Deuda Inicial ---
        OutlinedTextField(
            value = deuda,
            onValueChange = { deuda = it },
            label = { Text("Deuda inicial (€)") },
            placeholder = { Text("0.0") },
            leadingIcon = {
                Icon(Icons.Default.AttachMoney, contentDescription = "Icono de Dinero")
            },
            // Teclado decimal para permitir céntimos
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- 5. Botón de Guardar ---
        Button(
            onClick = {
                // Convertimos los Strings a sus tipos correctos antes de guardar
                val telLong = telefono.toLongOrNull() ?: 0L
                val deudaDouble = deuda.toDoubleOrNull() ?: 0.0

                // Validación básica: que el nombre no esté vacío
                if (nombre.isNotBlank()) {
                    onSaveCliente(nombre, telLong, deudaDouble)
                    onNavigateBack() // Volver atrás tras guardar
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Un botón alto es más cómodo de pulsar
            shape = RoundedCornerShape(12.dp),
            // Deshabilitar el botón si no hay nombre escrito
            enabled = nombre.isNotBlank()
        ) {
            Text(
                text = "Guardar Cliente",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
@Preview(showBackground = true, name = "Nuevo Cliente - Modo Claro")
@Composable
fun NewClienteScreenPreview() {
    PeluPosTheme {
        // Envolvemos en Surface para que coja el color de fondo de tu tema
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NewClienteScreen(
                onNavigateBack = {},
                onSaveCliente = { _, _, _ -> }
            )
        }
    }
}
