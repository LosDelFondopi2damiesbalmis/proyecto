package com.proyecto.PeluPos.ui.features.clientes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Cliente
import androidx.compose.ui.tooling.preview.Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteDetailScreen(
    cliente: Cliente?, // Recibimos el objeto completo
    onBack: () -> Unit,
    onEditClick: () -> Unit,
    onPayDebtClick: () -> Unit // Para el futuro: botón para saldar la deuda
) {
    if (cliente == null) {
        // Pantalla de error de seguridad por si no llega el cliente
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cliente no encontrado", color = MaterialTheme.colorScheme.error)
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) { Text("Volver") }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ficha del Cliente", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                },
                actions = {
                    // Botón de editar arriba a la derecha
                    IconButton(onClick = onEditClick) { Icon(Icons.Default.Edit, "Editar Cliente") }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. CABECERA DEL PERFIL ---
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = cliente.nombre.take(1).uppercase(),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = cliente.nombre,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            if (cliente.telefono != 0L) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = cliente.telefono.toString(), fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 2. TARJETA DE ESTADO ECONÓMICO ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (cliente.deuda > 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Deuda Acumulada",
                        style = MaterialTheme.typography.titleMedium,
                        color = if (cliente.deuda > 0) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    Text(
                        text = String.format("%.2f €", cliente.deuda),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = if (cliente.deuda > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    if (cliente.deuda > 0) {
                        Button(
                            onClick = onPayDebtClick,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Saldar Deuda")
                        }
                    } else {
                        Text(
                            text = "Cliente al corriente de pago",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_5", name = "Ficha - Cliente con Deuda")
@Composable
fun ClienteDetailScreenDeudaPreview() {
    MaterialTheme {
        Surface {
            ClienteDetailScreen(
                cliente = Cliente(1L, "Rafa Nadal", 150.50, 699888777L),
                onBack = {}, onEditClick = {}, onPayDebtClick = {}
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5", name = "Ficha - Cliente al día")
@Composable
fun ClienteDetailScreenSinDeudaPreview() {
    MaterialTheme {
        Surface {
            ClienteDetailScreen(
                cliente = Cliente(2L, "Einstein", 0.0, 611222333L),
                onBack = {}, onEditClick = {}, onPayDebtClick = {}
            )
        }
    }
}