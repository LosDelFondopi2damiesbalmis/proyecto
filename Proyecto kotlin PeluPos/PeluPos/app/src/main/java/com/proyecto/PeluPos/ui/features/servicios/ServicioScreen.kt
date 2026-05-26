import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Servicio
import com.proyecto.PeluPos.ui.features.empleados.EmpleadosEvent
import com.proyecto.PeluPos.ui.features.servicios.ServiciosEvent
import com.proyecto.PeluPos.ui.features.servicios.ServiciosUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosScreen(
    state: ServiciosUiState,
    onEvent: (ServiciosEvent) -> Unit,
    toggleSidebar: () -> Unit,
    // 🚨 PONLE EL (Long?) AQUÍ 🚨
    navigateToForm: (Long?) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // ON_RESUME significa "La pantalla acaba de aparecer frente al usuario"
            if (event == Lifecycle.Event.ON_RESUME) {
                // Llama al evento que recarga los datos desde tu base de datos
                onEvent(ServiciosEvent.CargarDatos)
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
                    navigateToForm(null)
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
                                navigateToForm(servicio.idServicio)
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
            Text("Servicios", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
            // ⚠️ AÑADIDO: Límite al texto de búsqueda para que no haga saltos raros
            placeholder = { Text("Buscar por servicio o empleado...", maxLines = 1, overflow = TextOverflow.Ellipsis) },
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
        BoxWithConstraints {
            val isCompact = this.maxWidth < 250.dp // Detecta si el menú lo ha aplastado

            if (isCompact) {
                // --- DISEÑO VERTICAL (Menú lateral abierto) ---
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = servicio.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)

                    if (servicio.descripcion.isNotBlank()) {
                        Text(text = servicio.descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 🚀 CAMBIO: Añadido weight(1f, fill = false) para que encoja si hace falta
                        Surface(
                            modifier = Modifier.weight(1f, fill = false),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSecondaryContainer)
                                Spacer(Modifier.width(4.dp))
                                Text(text = servicio.empleado.nombre, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }

                        // 🚀 CAMBIO: Un pequeño margen para que nunca se pegue la etiqueta al precio
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = "${String.format("%.2f", servicio.precio)}€", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary, maxLines = 1)
                    }
                }
            } else {
                // --- DISEÑO HORIZONTAL ORIGINAL (Pantalla normal) ---
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = servicio.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)

                        if (servicio.descripcion.isNotBlank()) {
                            Text(text = servicio.descripcion, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // 🚀 CAMBIO: Añadido también en el diseño normal por seguridad
                        Surface(
                            modifier = Modifier.weight(1f, fill = false),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSecondaryContainer)
                                Spacer(Modifier.width(4.dp))
                                Text(text = servicio.empleado.nombre, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondaryContainer, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(text = "${String.format("%.2f", servicio.precio)}€", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary, maxLines = 1)
                }
            }
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