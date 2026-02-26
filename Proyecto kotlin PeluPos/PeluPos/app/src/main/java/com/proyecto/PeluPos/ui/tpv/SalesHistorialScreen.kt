package com.proyecto.PeluPos.ui.tpv

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Estructura de datos para las facturas
data class Invoice(
    val id: String,
    val date: String,
    val time: String,
    val employee: String,
    val total: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesHistoryScreen(
    onBackClick: () -> Unit,
    onInvoiceClick: (String) -> Unit // Por si quieres abrir los detalles de la factura más adelante
) {
    // Datos de ejemplo simulando el historial de hoy
    val invoices = remember {
        listOf(
            Invoice("FAC-0012", "Hoy", "10:15", "Laura Gómez", 45.00),
            Invoice("FAC-0011", "Hoy", "09:30", "Carlos Ruiz", 18.50),
            Invoice("FAC-0010", "Ayer", "18:45", "Laura Gómez", 65.00),
            Invoice("FAC-0009", "Ayer", "16:20", "Marta Pérez", 12.00),
            Invoice("FAC-0008", "Ayer", "12:05", "Carlos Ruiz", 35.50)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Ventas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver al TPV")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(invoices) { invoice ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onInvoiceClick(invoice.id) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Icono y detalles básicos
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = "Factura",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = invoice.id,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "${invoice.date} • ${invoice.time}",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Atendido por ${invoice.employee}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Total de la factura
                        Text(
                            text = String.format("%.2f €", invoice.total),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
@Preview(showBackground = true, device = "id:pixel_5", name = "Historial de Ventas")
@Composable
fun SalesHistoryScreenPreview() {
    MaterialTheme {
        SalesHistoryScreen(
            onBackClick = {},
            onInvoiceClick = {}
        )
    }
}