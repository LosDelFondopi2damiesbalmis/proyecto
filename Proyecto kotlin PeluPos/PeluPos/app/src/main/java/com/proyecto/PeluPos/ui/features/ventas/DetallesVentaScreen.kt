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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material3.*
import androidx.compose.runtime.remember

import androidx.compose.ui.draw.clip

import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.Servicio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesVentaScreen(
    factura: Factura?, // <-- Ahora recibimos el objeto real
    onBack: () -> Unit
) {

    if (factura == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Factura no encontrada", color = MaterialTheme.colorScheme.error)
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) { Text("Volver") }
        }
        return
    }


    val sdf = remember { SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()) }
    val fechaFormateada = sdf.format(factura.fecha)


    val resumenItems = remember(factura) {
        val nombresServicios = factura.servicios.map { it.nombre }
        val nombresProductos = factura.productos.map { it.nombre }
        val todosLosItems = nombresServicios + nombresProductos
        if (todosLosItems.isEmpty()) "Sin ítems" else todosLosItems.joinToString(", ")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Ticket #${factura.idFactura}", // ID Real
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
                text = String.format("%.2f €", factura.monto), // Precio Real Formateado
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
                        value = resumenItems // Lista unificada de items
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    DetailRow(
                        icon = Icons.Default.Person,
                        label = "Atendido por",
                        value = factura.empleado.nombre // Nombre del empleado real
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    DetailRow(
                        icon = Icons.Default.CalendarToday,
                        label = "Fecha y Hora",
                        value = fechaFormateada // Fecha real formateada
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    DetailRow(
                        icon = if (factura.tipoPago == "Tarjeta") Icons.Default.CreditCard else Icons.Default.AttachMoney,
                        label = "Método de Pago",
                        value = factura.tipoPago // Método real
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    // ¡Añadimos al Cliente también ya que lo tenemos en el modelo!
                    DetailRow(
                        icon = Icons.Default.Face,
                        label = "Cliente",
                        value = factura.cliente.nombre
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. BOTONES DE ACCIÓN ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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

// --- HELPER PARA LAS FILAS DE DETALLE (Se queda igual) ---
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

// --- PREVIEW CON DATOS REALES ---
@Preview(showBackground = true)
@Composable
fun PreviewSaleDetail() {
    // Mocks
    val empleadoMock = Empleado(1L, telefono = 5323523, "carlos@pelu.com", "Peluquero", nombre = "Carlos")
    val clienteMock = Cliente(1L, "Juan Pérez", 0.0, 655111222)
    val servicioMock =
        Servicio(1L, "Corte Degradado + Barba", 25.0, "Corte y perfilado", empleadoMock)

    val facturaMock = Factura(
        idFactura = 123456789L,
        monto = 25.0,
        fecha = Date(),
        pendiente = false,
        tipoPago = "Tarjeta",
        cliente = clienteMock,
        empleado = empleadoMock,
        servicios = mutableListOf(servicioMock),
        productos = mutableListOf()
    )

    MaterialTheme { // Usa tu PeluPosTheme aquí en tu código real
        DetallesVentaScreen(factura = facturaMock, onBack = {})
    }
}