package com.proyecto.PeluPos.ui.features.locales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local
import com.proyecto.PeluPos.ui.features.empleados.EmpleadosEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalesScreen(
    state: LocalesUiState,
    onEvent: (LocalesEvent) -> Unit,
    toggleSidebar: () -> Unit,
    navigateToForm: (Long?) -> Unit,
    navigateToLocalDetail: (Long) -> Unit // 1. <-- AÑADIMOS ESTA FUNCIÓN
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // ON_RESUME significa "La pantalla acaba de aparecer frente al usuario"
            if (event == Lifecycle.Event.ON_RESUME) {
                // Llama al evento que recarga los datos desde tu base de datos
                onEvent(LocalesEvent.CargarDatos)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Locales", fontWeight = FontWeight.Bold) },

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(LocalesEvent.PrepararNuevoLocal)
                navigateToForm(null)
            }) {
                Icon(Icons.Default.Add, "Nuevo Local")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Buscador
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onEvent(LocalesEvent.OnSearchQueryChange(it)) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar por nombre o dirección...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.localesVisibles) { local ->
                    LocalCard(
                        local = local,
                        onEditClick = {
                            navigateToForm(local.idLocal)
                        },
                        onDetailClick = { // 2. <-- PASAMOS EL ID AL HACER CLIC
                            navigateToLocalDetail(local.idLocal)
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalCard(
    local: Local,
    onEditClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        onClick = onDetailClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Store, null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = local.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = local.direccion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Empleados asignados
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(4.dp))
                    if (local.empleadoCollection.isNotEmpty()) {
                        Text(
                            text = local.empleadoCollection.joinToString(", ") { it.nombre },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text("Sin equipo asignado", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline, fontStyle = FontStyle.Italic)
                    }
                }
            }

            IconButton(onClick = onEditClick, colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)) {
                Icon(Icons.Default.Edit, "Modificar Local")
            }
        }
    }
}

// --- PREVIEWS ---

private val mockEmpleado1 = Empleado(1L, 600123456L, "a@a.com", "Barbero", "Carlos", null)
private val mockEmpleado2 = Empleado(2L, 611222333L, "b@b.com", "Estilista", "Elena", null)

private val mockLocales = listOf(
    Local(
        1L,
        "Barbería Centro",
        "Av. Constitución 45",
        mutableListOf(mockEmpleado1, mockEmpleado2)
    ),
    Local(2L, "Local Norte", "C/ Gran Vía 12", mutableListOf())
)

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista de Locales")
@Composable
fun LocalesScreenPreview() {
    MaterialTheme {
        LocalesScreen(
            state = LocalesUiState(todosLosLocales = mockLocales, localesVisibles = mockLocales),
            onEvent = {},
            toggleSidebar = {},
            navigateToForm = {},
            navigateToLocalDetail = {}
        )
    }
}