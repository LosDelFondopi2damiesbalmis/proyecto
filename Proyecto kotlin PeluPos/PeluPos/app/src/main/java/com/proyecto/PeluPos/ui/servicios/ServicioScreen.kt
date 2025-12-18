package com.proyecto.PeluPos.ui.servicios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.ui.theme.PeluPosTheme

@Composable
fun ServiceSearchPage() {
    // ----------------------------------------------------
    // ESTADO (Luego irá en tu UiState)
    // ----------------------------------------------------
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    val categories = listOf("Todos", "Corte", "Color", "Tratamiento", "Estética")

    // Lista de servicios (Simulando strings directos)
    val allServices = remember {
        listOf(
            "Corte Caballero", "Corte Dama", "Tinte Raíz",
            "Mechas Balayage", "Hidratación", "Manicura", "Perfilado Barba"
        )
    }

    // Mapeo simple para simular categorías sin usar Data Class
    // Esto es solo para que el filtro funcione en el ejemplo
    fun getCategoryForService(service: String): String {
        return when {
            service.contains("Corte") -> "Corte"
            service.contains("Tinte") || service.contains("Mechas") -> "Color"
            service.contains("Hidratación") -> "Tratamiento"
            service.contains("Manicura") -> "Estética"
            else -> "Otros"
        }
    }

    // ----------------------------------------------------
    // LÓGICA DE FILTRADO
    // ----------------------------------------------------
    val filteredServices = remember(searchText, selectedCategory) {
        allServices.filter { service ->
            val matchesSearch = service.contains(searchText, ignoreCase = true)
            val matchesCategory = selectedCategory == "Todos" || getCategoryForService(service) == selectedCategory
            matchesSearch && matchesCategory
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- 1. Buscador ---
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar servicio") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // --- 2. Filtros de Categoría (Chips) ---
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = (selectedCategory == category),
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. Lista de Servicios ---
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(filteredServices) { serviceName ->
                ServiceListItem(
                    serviceName = serviceName,
                    category = getCategoryForService(serviceName)
                ) {
                    println("Seleccionado: $serviceName")
                }
            }
        }
    }
}

@Composable
fun ServiceListItem(
    serviceName: String,
    category: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Un pequeño indicador visual de categoría
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = serviceName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.Add, // O cualquier icono de acción
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ServiceSearchPreview()
{
    PeluPosTheme {
        ServiceSearchPage()
    }
}