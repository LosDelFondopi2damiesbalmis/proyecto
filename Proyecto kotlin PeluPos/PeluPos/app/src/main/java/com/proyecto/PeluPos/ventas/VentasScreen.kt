package com.proyecto.PeluPos.ventas

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
fun VentasScreen(
    toggleSidebar: () -> Unit,
    navigateToNewSale: () -> Unit,
    navigateToSaleDetail: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // DATOS CRUDOS (Sin clases, usando Mapas)
    // Claves: "id", "servicio", "fecha", "precio", "metodo", "empleado"
    val ventas = remember {
        listOf(
            mapOf(
                "id" to "V-001",
                "servicio" to "Corte Degradado",
                "fecha" to "Hoy, 10:00",
                "precio" to 15.0,
                "metodo" to "Efectivo",
                "empleado" to "Carlos"
            ),
            mapOf(
                "id" to "V-002",
                "servicio" to "Tinte + Mechas",
                "fecha" to "Hoy, 11:15",
                "precio" to 65.5,
                "metodo" to "Tarjeta",
                "empleado" to "Elena"
            ),
            mapOf(
                "id" to "V-003",
                "servicio" to "Cera Mate (Producto)",
                "fecha" to "Hoy, 11:30",
                "precio" to 12.0,
                "metodo" to "Efectivo",
                "empleado" to "Carlos"
            ),
            mapOf(
                "id" to "V-004",
                "servicio" to "Afeitado Clásico",
                "fecha" to "Ayer, 18:45",
                "precio" to 18.0,
                "metodo" to "Tarjeta",
                "empleado" to "Pedro"
            ),
            mapOf(
                "id" to "V-005",
                "servicio" to "Pack Algo",
                "fecha" to "Ayer, 16:00",
                "precio" to 120.0,
                "metodo" to "Transferencia",
                "empleado" to "Elena"
            )
        )
    }

    // Lógica de filtrado
    val filteredVentas = ventas.filter { venta ->
        val servicio = venta["servicio"] as String
        val empleado = venta["empleado"] as String

        servicio.contains(searchQuery, ignoreCase = true) ||
                empleado.contains(searchQuery, ignoreCase = true)
    }

    // Calcular total (sumando los Doubles del mapa)
    val totalFacturado = filteredVentas.sumOf { it["precio"] as Double }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        // --- HEADER ---
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                "Historial Ventas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // Resumen de dinero
            Text(
                text = "Total lista: ${totalFacturado}€",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Nueva Venta (Estilo ancho)
            Button(
                onClick = navigateToNewSale,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Nueva Venta / Ticket")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar servicio o empleado...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }

        // --- LISTA ---
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredVentas) { venta ->
                // Extraemos los datos del mapa para que sea más fácil leer
                val id = venta["id"] as String
                val servicio = venta["servicio"] as String
                val fecha = venta["fecha"] as String
                val precio = venta["precio"] as Double
                val metodo = venta["metodo"] as String
                val empleado = venta["empleado"] as String

                VentaCardMap(
                    servicio = servicio,
                    fecha = fecha,
                    precio = precio,
                    metodoPago = metodo,
                    empleado = empleado,
                    onClick = { navigateToSaleDetail(id) }
                )
            }
        }
    }
}

@Composable
fun VentaCardMap(
    servicio: String,
    fecha: String,
    precio: Double,
    metodoPago: String,
    empleado: String,
    onClick: () -> Unit
) {
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

            // INFO IZQUIERDA
            Row(modifier = Modifier.weight(1f)) {
                // Icono dinámico según texto
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (metodoPago == "Tarjeta")
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.tertiaryContainer, // Color diferente para efectivo
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (metodoPago == "Tarjeta") Icons.Default.CreditCard else Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = servicio,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$fecha • $empleado",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // INFO PRECIO DERECHA
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${precio}€",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = metodoPago,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
// --- PREVIEWS (Previsualizaciones) ---

@Preview(showBackground = true, name = "1. Pantalla Completa Ventas")
@Composable
fun SalesScreenPreview() {
    MaterialTheme {
        // Al no tener parámetros de datos, cargará los datos de prueba
        // que definiste dentro de la función SalesScreen
        VentasScreen(
            toggleSidebar = {},
            navigateToNewSale = {},
            navigateToSaleDetail = { id ->
                println("Navegar al detalle de: $id")
            }
        )
    }
}

@Preview(showBackground = true, name = "2. Tarjeta Venta (Tarjeta)")
@Composable
fun VentaCardPreview() {
    MaterialTheme {
        // Usamos un Box con padding para ver bien la sombra de la tarjeta
        Box(modifier = Modifier.padding(16.dp)) {
            VentaCardMap(
                servicio = "Alisado Keratina",
                fecha = "Hoy, 14:30",
                precio = 85.00,
                metodoPago = "Tarjeta", // Prueba cambiar a "Efectivo" para ver el icono
                empleado = "Lucía",
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "3. Tarjeta Venta (Efectivo)")
@Composable
fun VentaCardEfectivoPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            VentaCardMap(
                servicio = "Producto: Champú",
                fecha = "Ayer, 09:15",
                precio = 12.50,
                metodoPago = "Efectivo",
                empleado = "Carlos",
                onClick = {}
            )
        }
    }
}