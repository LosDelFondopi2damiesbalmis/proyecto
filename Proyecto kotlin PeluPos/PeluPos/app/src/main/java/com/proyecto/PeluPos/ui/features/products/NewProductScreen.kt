package com.proyecto.PeluPos.ui.features.products


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    state: ProductosUiState,
    onEvent: (ProductosEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val isEditing = state.editandoProductoId != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar Producto" else "Nuevo Producto", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            onEvent(ProductosEvent.BorrarProducto)
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.formNombre,
                onValueChange = { onEvent(ProductosEvent.OnNombreChange(it)) },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = state.formPrecioCompra,
                    onValueChange = { onEvent(ProductosEvent.OnPrecioCompraChange(it)) },
                    label = { Text("Precio Compra (€)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )

                OutlinedTextField(
                    value = state.formPrecioVenta,
                    onValueChange = { onEvent(ProductosEvent.OnPrecioVentaChange(it)) },
                    label = { Text("Precio Venta (€)") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = state.formStock,
                onValueChange = { onEvent(ProductosEvent.OnStockChange(it)) },
                label = { Text("Unidades en Stock") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onEvent(ProductosEvent.GuardarProducto)
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                enabled = state.isFormValid
            ) {
                Text(if (isEditing) "Actualizar Producto" else "Guardar Producto")
            }

            OutlinedButton(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Cancelar")
            }
        }
    }
}



@Preview(showBackground = true, device = "id:pixel_5", name = "2. Crear Producto")
@Composable
fun ProductoFormScreenPreview() {
    MaterialTheme {
        ProductoFormScreen(
            state = ProductosUiState(),
            onEvent = {}, onNavigateBack = {}
        )
    }
}