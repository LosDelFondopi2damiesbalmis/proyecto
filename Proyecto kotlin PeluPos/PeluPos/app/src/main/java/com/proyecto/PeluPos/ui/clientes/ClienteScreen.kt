package com.proyecto.PeluPos.ui.clientes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.ui.composables.Sidebar
import com.proyecto.PeluPos.ui.theme.PeluPosTheme


@Composable
fun ClientSearchPage() {
    // ----------------------------------------------------
    // ESTADO DE LA BÚSQUEDA
    // ----------------------------------------------------
    var searchText by remember { mutableStateOf("") }

    // ----------------------------------------------------
    // DATOS DE EJEMPLO (Solo una lista de Strings, simplificando el modelo de datos)
    // ----------------------------------------------------
    val allClientNames = remember {
        listOf(
            "Ana García Pérez",
            "Manuel López Ruiz",
            "Sara Martínez Cano",
            "Jorge Fernández Gil",
            "Laura Pérez Jiménez",
            "Carlos Gómez Díaz",
            "Elena Ruiz Soler",
            "David Torres Vega",
            "Alicia Valls Roig",
            "Pedro Sánchez Mora",
            "María José Ramos"
        )
    }

    // ----------------------------------------------------
    // LÓGICA DE FILTRADO
    // ----------------------------------------------------
    val filteredNames = remember(searchText, allClientNames) {
        if (searchText.isBlank()) {
            allClientNames // Mostrar todos si la caja de búsqueda está vacía
        } else {
            // Filtrar nombres que contengan el texto de búsqueda (sin importar mayúsculas/minúsculas)
            allClientNames.filter {
                it.contains(searchText, ignoreCase = true)
            }
        }
    }

    // ----------------------------------------------------
    // DISEÑO
    // ----------------------------------------------------
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // --- 1. Barra de Búsqueda (OutlinedTextField) ---
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText -> searchText = newText },
            label = { Text("Buscar Cliente") },
            placeholder = { Text("Introduce nombre...") },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Icono de Búsqueda"
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. Título y Contador ---
        Text(
            text = "Clientes (${filteredNames.size} encontrados)",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // --- 3. Lista de Resultados (LazyColumn) ---
        if (filteredNames.isEmpty() && searchText.isNotBlank()) {
            // Mensaje si no hay resultados
            Text(
                text = "No hay coincidencias para \"$searchText\".",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredNames) { name ->
                    ClientNameListItem(clientName = name) {
                        // Acción al hacer clic en el nombre del cliente
                        println("¡Cliente seleccionado: $name!")
                    }
                }
            }
        }
    }
}
@Composable
fun ClientNameListItem(clientName: String, onClick: () -> Unit) {
    // Usamos Surface o Card para un toque visual
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant // Color de fondo para que destaque un poco
    ) {
        Text(
            text = clientName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}
@Composable
fun ClienteScreen()
{
    var isSidebarVisible by remember { mutableStateOf(true) }
    Row(modifier = Modifier.fillMaxSize()) {
        Sidebar(
            isSidebarVisible = isSidebarVisible
        )
        IconButton(onClick = {isSidebarVisible = !isSidebarVisible}) {
            // Puedes usar un icono de "Menú" (si la barra está oculta) o "Flecha" (si está visible)
            Icon(
                imageVector = Icons.Default.Menu, // Usaremos solo Menú por simplicidad
                contentDescription = "Alternar Barra Lateral",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        ClientSearchPage()

    }
}

@Preview(showBackground = true)
@Composable
fun ClienteScreenPreview()
{
    PeluPosTheme {
        ClienteScreen()
    }
}