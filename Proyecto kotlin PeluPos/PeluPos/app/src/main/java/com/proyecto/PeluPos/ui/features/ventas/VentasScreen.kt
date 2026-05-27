package com.proyecto.PeluPos.ui.features.ventas

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Factura
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(
    facturas: List<Factura>,
    searchQuery: String,
    onEvent: (FacturacionEvent) -> Unit,
    toggleSidebar: () -> Unit,
    navigateToNewSale: () -> Unit,
    navigateToSaleDetail: (Long) -> Unit,
    onBack: () -> Unit,
) {
    // 🚀 RECARGA AUTOMÁTICA AL VOLVER A LA PANTALLA
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onEvent(FacturacionEvent.OnRecargarDatos)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val totalFacturado = facturas.sumOf { it.monto }
    val sdf = remember { SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 🚀 LO DEJAMOS VACÍO PARA QUE NO SE DUPLIQUE CON TU OTRO MENÚ */ },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // --- CABECERA ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Título grande movido aquí (si lo quieres ver una sola vez)
                Text(
                    text = "Historial de Ventas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Total lista: ${String.format("%.2f", totalFacturado)}€",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { onEvent(FacturacionEvent.OnSearchQueryChange(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar por empleado o cliente...") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // --- LISTA DE FACTURAS ---
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(facturas) { factura ->
                    val resumenServicio = factura.servicios.firstOrNull()?.nombre
                        ?: factura.productos.firstOrNull()?.nombre
                        ?: "Varios ítems"

                    VentaCardMap(
                        servicio = resumenServicio,
                        fecha = sdf.format(factura.fecha),
                        precio = factura.monto,
                        metodoPago = factura.tipoPago,
                        empleado = factura.empleado.nombre,
                        onClick = { navigateToSaleDetail(factura.idFactura) }
                    )
                }
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
        // 🚀 AÑADIMOS BOXWITHCONSTRAINTS PARA HACERLA RESPONSIVE
        BoxWithConstraints {
            val isCompact = this.maxWidth < 300.dp

            if (isCompact) {
                // --- DISEÑO VERTICAL (Para pantallas/ventanas muy estrechas) ---
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (metodoPago == "Tarjeta") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (metodoPago == "Tarjeta") Icons.Default.CreditCard else Icons.Default.AttachMoney,
                                    contentDescription = null,
                                    tint = if (metodoPago == "Tarjeta") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }

                        Text(
                            text = String.format("%.2f €", precio),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = servicio,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$fecha • $empleado",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = metodoPago,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                // --- DISEÑO HORIZONTAL ORIGINAL (Para vistas normales) ---
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (metodoPago == "Tarjeta") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (metodoPago == "Tarjeta") Icons.Default.CreditCard else Icons.Default.AttachMoney,
                                    contentDescription = null,
                                    tint = if (metodoPago == "Tarjeta") MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
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

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = String.format("%.2f €", precio),
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
    }
}
// --- PREVIEWS (Previsualizaciones) ---

@Preview(
    showBackground = true,
    device = "id:pixel_8",
    name = "Pantalla Historial Ventas"
)
@Composable
fun VentasScreenPreview() {
    // 1. Creamos dependencias falsas (Mock)
    val empleadoMock = Empleado(
        idEmpleado = 1L,
        nombre = "Carlos",
        email = "carlos@pelupos.com",
        telefono = 600123456,
        cargo = "Peluquero"
    )

    val clienteMock = Cliente(
        idCliente = 1L,
        nombre = "Juan Pérez",
        deuda = 5.5,
        telefono = 655111222
    )

    val servicioMock = Servicio(
        idServicio = 1L,
        nombre = "Corte Degradado",
        precio = 15.0,
        descripcion = "Corte a máquina",
        empleado = empleadoMock
    )

    val productoMock = Producto(
        idProducto = 1L,
        nombre = "Cera Mate",
        precioCompra = 5.0,
        precioVenta = 12.0,
        stock = 10
    )

    // 2. Creamos una lista de facturas de prueba
    val mockFacturas = listOf(
        // Factura 1: Solo un servicio (Efectivo)
        Factura(
            idFactura = 1001L,
            monto = 15.0,
            fecha = Date(System.currentTimeMillis() - 1000 * 60 * 30), // Hace 30 min
            pendiente = false,
            tipoPago = "Efectivo",
            cliente = clienteMock,
            empleado = empleadoMock,
            servicios = mutableListOf(servicioMock),
            productos = mutableListOf()
        ),
        // Factura 2: Solo un producto (Tarjeta)
        Factura(
            idFactura = 1002L,
            monto = 12.0,
            fecha = Date(System.currentTimeMillis() - 1000 * 60 * 120), // Hace 2 horas
            pendiente = false,
            tipoPago = "Tarjeta",
            cliente = clienteMock,
            empleado = empleadoMock,
            servicios = mutableListOf(),
            productos = mutableListOf(productoMock)
        ),
        // Factura 3: Servicio + Producto
        Factura(
            idFactura = 1003L,
            monto = 27.0,
            fecha = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24), // Ayer
            pendiente = false,
            tipoPago = "Tarjeta",
            cliente = clienteMock,
            empleado = empleadoMock,
            servicios = mutableListOf(servicioMock),
            productos = mutableListOf(productoMock)
        )
    )

    // 3. Renderizamos la pantalla con el tema
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            VentasScreen(
                facturas = mockFacturas,
                searchQuery = "", // Prueba a cambiar esto por "Carlos" para simular que hay texto
                onEvent = {},
                toggleSidebar = {},
                navigateToNewSale = {},
                navigateToSaleDetail = {},
                onBack = {}
            )
        }
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