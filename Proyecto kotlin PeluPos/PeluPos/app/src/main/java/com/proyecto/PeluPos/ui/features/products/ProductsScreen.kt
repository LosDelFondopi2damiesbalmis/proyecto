package com.proyecto.PeluPos.ui.features.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.ui.features.empleados.EmpleadosEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    state: ProductosUiState,
    onEvent: (ProductosEvent) -> Unit,
    toggleSidebar: () -> Unit,
    // 🚨 CAMBIA ESTA LÍNEA: Ponle el (Long?)
    navigateToForm: (Long?) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            // ON_RESUME significa "La pantalla acaba de aparecer frente al usuario"
            if (event == Lifecycle.Event.ON_RESUME) {
                // Llama al evento que recarga los datos desde tu base de datos
                onEvent(ProductosEvent.CargarDatos)
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
                title = { Text("Inventario", fontWeight = FontWeight.Bold) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // 1. Borramos el evento viejo (ya no hace falta)
                    // onEvent(ProductosEvent.PrepararNuevoProducto)

                    // 2. Le pasamos 'null' para indicarle al grafo que vamos a CREAR
                    navigateToForm(null)
                }
            ) {
                Icon(Icons.Default.Add, "Nuevo Producto")
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
                onValueChange = { onEvent(ProductosEvent.OnSearchQueryChange(it)) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar producto...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Resumen estadístico
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Separación limpia
            ) {
                // El Modifier.weight(1f) hace que los 3 ocupen exactamente un 33% del ancho
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    SummaryItem("Total", "${state.totalProductos}")
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    SummaryItem("Stock bajo", "${state.stockBajoCount}")
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    SummaryItem("Valor Inven.", String.format("%.2f€", state.valorTotal))
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            if (state.productosVisibles.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay productos", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.productosVisibles) { producto ->
                        ProductCard(
                            producto = producto,
                            onClick = {
                                navigateToForm(producto.idProducto)
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 2.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1, // Evita saltos de línea
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1, // Evita saltos de línea
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(producto: Producto, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        // 🚀 LA MAGIA RESPONSIVE:
        BoxWithConstraints {
            val isCompact = this.maxWidth < 250.dp // Si es muy estrecho

            if (isCompact) {
                // --- DISEÑO VERTICAL (Menú lateral abierto) ---
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Compra: ${String.format("%.2f", producto.precioCompra)} €",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${String.format("%.2f", producto.precioVenta)} €",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        StockIndicator(stock = producto.stock)
                    }
                }
            } else {
                // --- DISEÑO HORIZONTAL ORIGINAL (Pantalla normal) ---
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = producto.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Compra: ${String.format("%.2f", producto.precioCompra)} €",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "${String.format("%.2f", producto.precioVenta)} €",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1
                        )
                        StockIndicator(stock = producto.stock)
                    }
                }
            }
        }
    }
}

@Composable
fun StockIndicator(stock: Int) {
    val (backgroundColor, textColor, text) = when {
        stock == 0 -> Triple(MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.error, "AGOTADO")
        stock < 5 -> Triple(Color(0xFFFFB347).copy(alpha = 0.2f), Color(0xFFCC7A00), "BAJO: $stock")
        else -> Triple(Color(0xFF4CAF50).copy(alpha = 0.2f), Color(0xFF2E7D32), "$stock uds")
    }

    Box(
        modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(backgroundColor).padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, color = textColor)
    }
}
private val mockProductos = listOf(
    Producto(1L, "Champú Reparador Kerastase", 12.0, 24.99, 15),
    Producto(2L, "Tinte Wella Koleston", 6.50, 12.50, 3),
    Producto(3L, "Tijeras Profesionales", 45.0, 89.99, 0)
)

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista Productos")
@Composable
fun ProductosScreenPreview() {
    MaterialTheme {
        ProductosScreen(
            state = ProductosUiState(productosVisibles = mockProductos, todosLosProductos = mockProductos),
            onEvent = {}, toggleSidebar = {}, navigateToForm = {}
        )
    }
}