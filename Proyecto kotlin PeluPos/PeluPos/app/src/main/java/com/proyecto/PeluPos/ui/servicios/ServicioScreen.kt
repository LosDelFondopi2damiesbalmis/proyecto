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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.data.mocks.EmpleadoMock
import com.proyecto.PeluPos.data.mocks.ServicioMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    toggleSidebar: () -> Unit,
    navigateToServiceDetail: (serviceId: Long) -> Unit,
    navigateToNewService: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // Lista usando tu Data Class ServicioMock
    val servicios = remember {
        listOf(
            ServicioMock(
                1L,
                "Corte Degradado",
                15.0,
                "Corte moderno con acabado en navaja",
                EmpleadoMock(nombre = "Carlos")
            ),
            ServicioMock(2L, "Color Completo", 45.0, "Tinte de raíz a puntas", EmpleadoMock(nombre = "Elena")),
            ServicioMock(3L, "Barba Ritual", 12.0, "Arreglo de barba con toalla caliente", EmpleadoMock(nombre = "Carlos")),
            ServicioMock(4L, "Tratamiento Keratina", 85.0, "Hidratación profunda", EmpleadoMock(nombre = "Lucía"))
        )
    }

    val filteredServices = servicios.filter {
        it.nombre.contains(searchQuery, ignoreCase = true) ||
                it.empleado.nombre.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // Cabecera simplificada
        HeaderServicios(
            searchQuery = searchQuery,
            onSearchChange = { searchQuery = it },
            onAddClick = navigateToNewService
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredServices) { servicio ->
                ServiceCard(
                    servicio = servicio,
                    onClick = { navigateToServiceDetail(servicio.idServicio) }
                )
            }
        }
    }
}
@Composable
fun ServiceCard(
    servicio: ServicioMock,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = servicio.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = servicio.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Badge del Empleado Asignado
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = servicio.empleado.nombre,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Precio destacado
            Text(
                text = "${servicio.precio}€",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
@Composable
fun HeaderServicios(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Nuestros Servicios",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onAddClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por servicio o empleado...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}

@Preview(showBackground = true, name = "Pantalla Completa")
@Composable
fun ServicesScreenPreview() {
    MaterialTheme {
        // Simulamos la navegación con lambdas vacías
        ServicesScreen(
            toggleSidebar = {},
            navigateToServiceDetail = {},
            navigateToNewService = {}
        )
    }
}

@Preview(showBackground = true, name = "Tarjeta Individual")
@Composable
fun ServiceCardPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ServiceCard(
                servicio = ServicioMock(
                    idServicio = 1L,
                    nombre = "Balayage Profesional",
                    precio = 120.50,
                    descripcion = "Técnica de aclarado degradado natural",
                    empleado = EmpleadoMock(nombre = "Lucía García")
                ),
                onClick = {}
            )
        }
    }
}