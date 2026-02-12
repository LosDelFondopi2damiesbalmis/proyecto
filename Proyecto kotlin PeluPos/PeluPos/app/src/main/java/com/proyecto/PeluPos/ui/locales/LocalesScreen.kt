package com.proyecto.PeluPos.ui.locales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LocalesScreen(
    toggleSidebar: () -> Unit,
    navigateToLocalDetail: (localId: Int) -> Unit,
    navigateToNewLocal: () -> Unit,
    navigateToEditLocal: (localId: Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // DATOS DE PRUEBA (Sin clases externas, usando Triple: ID, Nombre, Dirección)
    val locales = remember {
        listOf(
            Triple(1, "Barbería Centro", "Av. de la Constitución 45, Madrid"),
            Triple(2, "Sede Norte", "C/ Gran Vía 12, Bilbao"),
            Triple(3, "Local Estación", "Plaza de la Estación s/n, Valencia"),
            Triple(4, "Barbería Sur", "Av. Andalucía 88, Sevilla"),
            Triple(5, "Corner C.Comercial", "C.C. Las Arenas, Planta 2, Barcelona")
        )
    }

    // Lógica de filtrado
    val filteredLocales = locales.filter {
        it.second.contains(searchQuery, ignoreCase = true) || // Por nombre
                it.third.contains(searchQuery, ignoreCase = true)     // Por dirección
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        // Cabecera con Botón de CREAR
        HeaderLocales(
            searchQuery = searchQuery,
            onSearchChange = { searchQuery = it },
            onAddClick = navigateToNewLocal
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredLocales) { local ->
                // local.first = ID, local.second = Nombre, local.third = Dirección
                LocalCard(
                    nombre = local.second,
                    direccion = local.third,
                    onClick = { navigateToLocalDetail(local.first) },
                    onEditClick = { navigateToEditLocal(local.first) }
                )
            }
        }
    }
}

@Composable
fun LocalCard(
    nombre: String,
    direccion: String,
    onClick: () -> Unit,
    onEditClick: () -> Unit
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
            // Icono del local a la izquierda
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Store, // Icono de tienda
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información central (Nombre y dirección)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = direccion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Botón de MODIFICAR a la derecha
            IconButton(
                onClick = onEditClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Modificar Local")
            }
//            Spacer(modifier = Modifier.height(12.dp))
//            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // 2. CAMBIO VISUAL: Mostrar los empleados
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = null,
//                    modifier = Modifier.size(16.dp),
//                    tint = MaterialTheme.colorScheme.secondary
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//
//                if (local.empleados.isNotEmpty()) {
//                    // TRUCO: joinToString convierte la lista ["Juan", "Ana"] en "Juan, Ana"
//                    Text(
//                        text = local.empleados.joinToString(", "),
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                } else {
//                    Text(
//                        text = "Sin equipo asignado",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.outline,
//                        fontStyle = FontStyle.Italic
//                    )
//                }
//            }
        }
    }
}

@Composable
fun HeaderLocales(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

            Text(
                "Mis Locales",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // Botón para CREAR NUEVO LOCAL
            Button(
                onClick = onAddClick,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Nuevo Local")
            }


        Spacer(modifier = Modifier.height(16.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por nombre o dirección...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

// --- PREVIEWS ---

@Preview(showBackground = true, name = "Pantalla Locales")
@Composable
fun LocalesScreenPreview() {
    MaterialTheme {
        LocalesScreen(
            toggleSidebar = {},
            navigateToLocalDetail = {},
            navigateToNewLocal = {},
            navigateToEditLocal = {}
        )
    }
}

@Preview(showBackground = true, name = "Tarjeta Local")
@Composable
fun LocalCardPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            LocalCard(
                nombre = "Barbería Calle Mayor",
                direccion = "C/ Mayor 10, 1ºB, Madrid",
                onClick = {},
                onEditClick = {}
            )
        }
    }
}