package com.proyecto.PeluPos.ui.features.clientes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesScreen(
    state: ClientesUiState,
    onEvent: (ClientesEvent) -> Unit,
    toggleSidebar: () -> Unit,
    navigateToNewCliente: () -> Unit,
    navigateToClienteDetail: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Clientes", fontWeight = FontWeight.Bold) },
               
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ClientesEvent.PrepararNuevoCliente)
                navigateToNewCliente()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Cliente")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // --- 1. Barra de Búsqueda ---
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onEvent(ClientesEvent.OnSearchQueryChange(it)) },
                label = { Text("Buscar Cliente") },
                placeholder = { Text("Introduce nombre...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            // --- 2. Contador de resultados ---
            Text(
                text = "Clientes (${state.clientesVisibles.size})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
            )

            // --- 3. Lista ---
            if (state.clientesVisibles.isEmpty() && state.searchQuery.isNotBlank()) {
                Text("No hay coincidencias para \"${state.searchQuery}\".", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.clientesVisibles) { cliente ->
                        ClienteListItem(
                            cliente = cliente,
                            onEditClick = {
                                onEvent(ClientesEvent.PrepararEdicion(cliente.idCliente))
                                navigateToNewCliente() // Usamos la misma ruta para editar
                            },
                            onDetailClick = { navigateToClienteDetail(cliente.idCliente) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) } // Espacio para FAB
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteListItem(cliente: Cliente, onEditClick: () -> Unit, onDetailClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        onClick = onDetailClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con Inicial
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(46.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = cliente.nombre.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = cliente.nombre, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                if (cliente.telefono != 0L) {
                    Text(text = "Tel: ${cliente.telefono}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                // Resaltar Deuda
                if (cliente.deuda > 0) {
                    Text(
                        text = String.format("Deuda: %.2f €", cliente.deuda),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

private val mockClientesLista = listOf(
    Cliente(idCliente = 1L, nombre = "Diddy", deuda = 500000.0, telefono = 654380200L),
    Cliente(idCliente = 2L, nombre = "Einstein", deuda = 0.0, telefono = 611222333L),
    Cliente(idCliente = 3L, nombre = "Rafa Nadal", deuda = 15.50, telefono = 699888777L)
)

// ==========================================
// 1. PREVIEW: LISTA DE CLIENTES
// ==========================================
@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista de Clientes")
@Composable
fun ClienteScreenPreview() {
    PeluPosTheme { // Usamos tu tema personalizado
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ClientesScreen(
                // Le pasamos un estado simulado con nuestra lista mockeada
                state = ClientesUiState(
                    todosLosClientes = mockClientesLista,
                    clientesVisibles = mockClientesLista,
                    searchQuery = ""
                ),
                onEvent = {}, // Vacío porque en Preview no hacemos clics reales
                toggleSidebar = {},
                navigateToNewCliente = {},
                navigateToClienteDetail = {}
            )
        }
    }
}