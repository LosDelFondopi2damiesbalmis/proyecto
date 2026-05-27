package com.proyecto.PeluPos.ui.features.clientes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteFormScreen(
    state: ClientesUiState,
    onEvent: (ClientesEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val isEditing = state.editandoClienteId != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Modificar Cliente" else "Nuevo Cliente", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                // 🚀 NUEVO: Botón de borrar a la derecha
                actions = {
                    if (isEditing) {
                        IconButton(
                            onClick = {
                                onEvent(ClientesEvent.BorrarCliente)
                                onNavigateBack() // Volvemos a la lista tras darle a borrar
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar Cliente",
                                tint = MaterialTheme.colorScheme.error // Lo ponemos en rojo
                            )
                        }
                    }
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
            OutlinedTextField(
                value = state.formNombre,
                onValueChange = { onEvent(ClientesEvent.OnNombreChange(it)) },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, "Persona") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = state.formTelefono,
                onValueChange = { onEvent(ClientesEvent.OnTelefonoChange(it)) },
                label = { Text("Teléfono") },
                leadingIcon = { Icon(Icons.Default.Phone, "Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = state.formDeuda,
                onValueChange = { onEvent(ClientesEvent.OnDeudaChange(it)) },
                label = { Text("Deuda actual (€)") },
                leadingIcon = { Icon(Icons.Default.AttachMoney, "Dinero") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onEvent(ClientesEvent.GuardarCliente)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = state.isFormValid
            ) {
                Text(if (isEditing) "Actualizar Cliente" else "Guardar Cliente", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
@Preview(showBackground = true, device = "id:pixel_5", name = "2. Formulario Cliente")
@Composable
fun ClienteFormPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ClienteFormScreen(
                state = ClientesUiState(formNombre = "Ana García", formTelefono = "600123456", formDeuda = "0.0"),
                onEvent = {}, onNavigateBack = {}
            )
        }
    }
}