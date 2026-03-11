import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Servicio
import com.proyecto.PeluPos.ui.features.servicios.ServiciosEvent
import com.proyecto.PeluPos.ui.features.servicios.ServiciosUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosScreen(
    state: ServiciosUiState,
    onEvent: (ServiciosEvent) -> Unit,
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Servicios", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            HeaderServicios(
                searchQuery = state.searchQuery,
                onSearchChange = { onEvent(ServiciosEvent.OnSearchQueryChange(it)) },
                onAddClick = {
                    onEvent(ServiciosEvent.PrepararNuevoServicio)
                    navigateToForm()
                }
            )

            if (state.serviciosVisibles.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay servicios disponibles", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.serviciosVisibles) { servicio ->
                        ServiceCard(
                            servicio = servicio,
                            onClick = {
                                onEvent(ServiciosEvent.PrepararEdicion(servicio.idServicio))
                                navigateToForm()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderServicios(searchQuery: String, onSearchChange: (String) -> Unit, onAddClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Servicios", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            IconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) { Icon(Icons.Default.Add, "Añadir") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por servicio o empleado...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceCard(servicio: Servicio, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = servicio.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                if (servicio.descripcion.isNotBlank()) {
                    Text(text = servicio.descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSecondaryContainer)
                        Spacer(Modifier.width(4.dp))
                        Text(text = servicio.empleado.nombre, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }

            Text(text = "${String.format("%.2f", servicio.precio)}€", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

private val mockEmpleado = Empleado(1L, 63283939L, "carlos@gmail.com", "Barbero", "Carlos", null)
private val mockServicios = listOf(
    Servicio(1L, "Corte Degradado", 15.0, "Corte moderno con acabado en navaja", mockEmpleado),
    Servicio(2L, "Tratamiento Keratina", 85.0, "Hidratación profunda", mockEmpleado)
)

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista de Servicios")
@Composable
fun ServiciosScreenPreview() {
    MaterialTheme {
        ServiciosScreen(
            state = ServiciosUiState(serviciosVisibles = mockServicios, todosLosServicios = mockServicios),
            onEvent = {}, toggleSidebar = {}, navigateToForm = {}
        )
    }
}