package com.proyecto.PeluPos.ui.features.ventas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.ui.theme.PeluPosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesVentaScreen(
    saleId: String?, // Recibimos el ID desde la navegación
    onBack: () -> Unit
) {
    // --- DATOS SIMULADOS (En una app real, pedirías esto al ViewModel/Base de Datos usando el saleId) ---
    val detalleVenta = remember(saleId) {
        mapOf(
            "id" to (saleId ?: "V-000"),
            "servicio" to "Corte Degradado + Barba",
            "precio" to 25.0,
            "empleado" to "Carlos",
            "fecha" to "19 Ene 2024, 10:30",
            "metodo" to "Tarjeta",
            "estado" to "Pagado"
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Ticket #${detalleVenta["id"]}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- 1. ENCABEZADO DE PRECIO ---
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${detalleVenta["precio"]}0€",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Venta completada",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- 2. TARJETA DE DETALLES ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailRow(
                        icon = Icons.Default.ContentCut,
                        label = "Servicio/Producto",
                        value = detalleVenta["servicio"].toString()
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    DetailRow(
                        icon = Icons.Default.Person,
                        label = "Atendido por",
                        value = detalleVenta["empleado"].toString()
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    DetailRow(
                        icon = Icons.Default.CalendarToday,
                        label = "Fecha y Hora",
                        value = detalleVenta["fecha"].toString()
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    DetailRow(
                        icon = if (detalleVenta["metodo"] == "Tarjeta") Icons.Default.CreditCard else Icons.Default.AttachMoney,
                        label = "Método de Pago",
                        value = detalleVenta["metodo"].toString()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. BOTONES DE ACCIÓN ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón Anular (Estilo secundario/error)
                OutlinedButton(
                    onClick = { /* Lógica devolución */ },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Outlined.Delete, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Devolución")
                }

                // Botón Imprimir (Estilo primario)
                Button(
                    onClick = { /* Lógica imprimir ticket */ },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Outlined.Print, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Imprimir")
                }
            }
        }
    }
}

// --- HELPER PARA LAS FILAS DE DETALLE ---
@Composable
fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer, modifier = Modifier.size(20.dp))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.outline)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewSaleDetail() {
    PeluPosTheme {
        DetallesVentaScreen(saleId = "V-1234", onBack = {})
    }
}