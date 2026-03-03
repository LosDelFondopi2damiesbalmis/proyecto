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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.ui.theme.PeluPosTheme


@Composable
fun ClientSearchPage(listaClientes : List<Cliente>) {
    // ----------------------------------------------------
    // ESTADO DE LA BÚSQUEDA
    // ----------------------------------------------------
    var searchText by remember { mutableStateOf("") }

    val filteredNames = remember(searchText, listaClientes) {
        if (searchText.isBlank()) {
            listaClientes // Mostrar todos si la caja de búsqueda está vacía
        } else {
            // Filtrar nombres que contengan el texto de búsqueda (sin importar mayúsculas/minúsculas)
            listaClientes.filter {
                it.nombre.contains(searchText, ignoreCase = true)
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
                    ClientNameListItem(clientName = name.nombre) {
                        // Acción al hacer clic en el nombre del cliente
                        println("¡Cliente seleccionado: $name!")
                    }
                }
            }
        }
    }
}
@Composable
fun ClientNameListItem(
    clientName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp), // Más aire a los lados
        shape = RoundedCornerShape(12.dp), // Bordes más redondeados y modernos
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp // Sutil sombra para dar profundidad
        ),
        onClick = onClick // El Card ya maneja el ripple effect internamente
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Un icono circular con la inicial mejora mucho la identidad visual
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = clientName.take(1).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = clientName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
@Composable
fun ClienteScreen(
    toggleSidebar: () -> Unit,
    navigateToClienteDetail: (serviceId: Long) -> Unit,
    navigateToNewCliente: () -> Unit
)
{
   val clientes : List<Cliente> = listOf(
       Cliente(
           idCliente = 1L,
           nombre = "diddy",
           deuda = 500000.0,
           telefono = 6543802
       ),
       Cliente(
           idCliente = 1L,
           nombre = "einstein",
           deuda = 500000.0,
           telefono = 6543802
       ),
       Cliente(
           idCliente = 1L,
           nombre = "rafa nadal",
           deuda = 500000.0,
           telefono = 6543802
       ),
   )
        ClientSearchPage(clientes)

}

@Preview(showBackground = true)
@Composable
fun ClienteScreenPreview()
{
    PeluPosTheme {
        ClienteScreen(toggleSidebar = {},
            navigateToClienteDetail = {},
            navigateToNewCliente = {})
    }
}