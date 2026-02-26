package com.proyecto.PeluPos.ui.locales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.ui.composables.SelectorEmpleados

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateLocalScreen(
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    // Estados del formulario (inician vacíos)
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    val todosLosEmpleados = listOf("Carlos", "Elena", "Lucía", "Pedro", "Sofía")
    val empleadosSeleccionados = remember { mutableStateListOf<String>() }
    var descripcion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Local", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Permite scroll si el teclado tapa
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Sección de Icono Decorativo
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(100),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AddHome, // Icono estándar
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // CAMPOS DE TEXTO
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Local") },
                leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

//            OutlinedTextField(
//                value = telefono,
//                onValueChange = { telefono = it },
//                label = { Text("Teléfono de contacto") },
//                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )

//            OutlinedTextField(
//                value = descripcion,
//                onValueChange = { descripcion = it },
//                label = { Text("Descripción o notas (Opcional)") },
//                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
//                modifier = Modifier.fillMaxWidth(),
//                minLines = 3
//            )
            HorizontalDivider()

            Text("Equipo Asignado", style = MaterialTheme.typography.titleMedium)

            // ---------------------------------------------------------
            // AQUI USAMOS TU NUEVO COMPONENTE
            // ---------------------------------------------------------
            SelectorEmpleados(
                empleadosDisponibles = todosLosEmpleados,
                onEmpleadoSeleccionado = { empleadoElegido ->
                    // Lógica simple: Si no está en la lista, lo añadimos
                    if (!empleadosSeleccionados.contains(empleadoElegido)) {
                        empleadosSeleccionados.add(empleadoElegido)
                    }
                }
            )
            // ---------------------------------------------------------

            // Renderizado de los Chips (Las etiquetas de los seleccionados)
            if (empleadosSeleccionados.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    empleadosSeleccionados.forEach { empleado ->
                        InputChip(
                            selected = true,
                            onClick = { empleadosSeleccionados.remove(empleado) },
                            label = { Text(empleado) },
                            trailingIcon = {
                                Icon(Icons.Default.Close, contentDescription = "Borrar", modifier = Modifier.size(16.dp))
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN GUARDAR
            Button(
                onClick = onSaveSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Crear Local")
            }
        }
    }
}
@Preview(showBackground = true, name = "Crear Local")
@Composable
fun CreateLocalPreview() {
    MaterialTheme {
        CreateLocalScreen(onNavigateBack = {}, onSaveSuccess = {})
    }
}