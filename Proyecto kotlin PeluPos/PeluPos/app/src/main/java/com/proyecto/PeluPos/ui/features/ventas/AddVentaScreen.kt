package com.proyecto.PeluPos.ui.features.ventas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Cliente
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSaleScreen(
    // Datos del Carrito (Vienen del TPV)
    productosCart: List<Producto>,
    serviciosCart: List<Servicio>,

    // Datos para el formulario
    empleadosDisponibles: List<Empleado>,
    clientesDisponibles: List<Cliente>,

    // Estado seleccionado
    empleadoSeleccionado: Empleado?,
    clienteSeleccionado: Cliente?,
    tipoPago: String,

    // Eventos y Navegación
    onEvent: (FacturacionEvent) -> Unit,
    onBack: () -> Unit,
    onFacturaGuardada: () -> Unit // Callback para volver al TPV o Historial al terminar
) {
    var expandedEmpleado by remember { mutableStateOf(false) }
    var expandedCliente by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Cálculo automático del total del ticket
    val totalAmount = productosCart.sumOf { it.precioVenta } + serviciosCart.sumOf { it.precio }
    val isFormValid = empleadoSeleccionado != null && clienteSeleccionado != null

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cerrar Ticket", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // --- RESUMEN DEL CARRITO ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total a cobrar:", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = String.format("%.2f €", totalAmount),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Divider()

            // --- 1. SECCIÓN: EMPLEADO ---
            Text("¿Quién ha realizado el servicio?", color = MaterialTheme.colorScheme.primary)
            ExposedDropdownMenuBox(
                expanded = expandedEmpleado,
                onExpandedChange = { expandedEmpleado = !expandedEmpleado }
            ) {
                OutlinedTextField(
                    value = empleadoSeleccionado?.nombre ?: "Seleccionar Empleado",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedEmpleado) }
                )
                ExposedDropdownMenu(expanded = expandedEmpleado, onDismissRequest = { expandedEmpleado = false }) {
                    empleadosDisponibles.forEach { emp ->
                        DropdownMenuItem(
                            text = { Text(emp.nombre) },
                            onClick = {
                                onEvent(FacturacionEvent.OnEmpleadoSeleccionado(emp))
                                expandedEmpleado = false
                            }
                        )
                    }
                }
            }

            // --- 2. SECCIÓN: CLIENTE ---
            Text("Asignar a Cliente", color = MaterialTheme.colorScheme.primary)
            ExposedDropdownMenuBox(
                expanded = expandedCliente,
                onExpandedChange = { expandedCliente = !expandedCliente }
            ) {
                OutlinedTextField(
                    value = clienteSeleccionado?.nombre ?: "Seleccionar Cliente",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedCliente) }
                )
                ExposedDropdownMenu(expanded = expandedCliente, onDismissRequest = { expandedCliente = false }) {
                    clientesDisponibles.forEach { cli ->
                        DropdownMenuItem(
                            text = { Text("${cli.nombre} - ${cli.telefono}") },
                            onClick = {
                                onEvent(FacturacionEvent.OnClienteSeleccionado(cli))
                                expandedCliente = false
                            }
                        )
                    }
                }
            }

            Divider()

            // --- 3. SECCIÓN: MÉTODO DE PAGO ---
            Text("Método de Pago", color = MaterialTheme.colorScheme.primary)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PaymentOptionButton(
                    text = "Efectivo",
                    icon = Icons.Default.AttachMoney,
                    isSelected = tipoPago == "Efectivo",
                    onClick = { onEvent(FacturacionEvent.OnTipoPagoSeleccionado("Efectivo")) },
                    modifier = Modifier.weight(1f)
                )
                PaymentOptionButton(
                    text = "Tarjeta",
                    icon = Icons.Default.CreditCard,
                    isSelected = tipoPago == "Tarjeta",
                    onClick = { onEvent(FacturacionEvent.OnTipoPagoSeleccionado("Tarjeta")) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- BOTÓN FINAL ---
            Button(
                onClick = {
                    onEvent(FacturacionEvent.OnGuardarFactura)
                    onFacturaGuardada()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = isFormValid // Solo se habilita si hay empleado y cliente seleccionados
            ) {
                Text("Cobrar e Imprimir Ticket", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
fun PaymentOptionButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)

    Surface(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = contentColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, fontWeight = FontWeight.Bold, color = contentColor)
        }
    }
}

// Función simple para obtener la hora actual (simulada como string)
fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date())
}


// --- PREVIEW ---
@Preview(
    showBackground = true,
    device = "id:pixel_5",
    name = "Pantalla Cerrar Ticket (Crear Factura)"
)
@Composable
fun AddSaleScreenPreview() {
    // 1. Mocks de Empleados y Clientes disponibles
    val empleadoMock1 = Empleado(
        idEmpleado = 1L,
        nombre = "Carlos",
        email = "carlos@pelupos.com",
        telefono = 600123456,
        cargo = "Peluquero"
    )

    val empleadoMock2 = Empleado(
        idEmpleado = 2L,
        nombre = "Elena",
        email = "elena@pelupos.com",
        telefono = 600654321,
        cargo = "Estilista"
    )

    val clienteMock1 = Cliente(
        idCliente = 1L,
        nombre = "Juan Pérez",
        deuda = 0.0, // <-- Le pasamos un número porque el modelo pide deuda, no email
        telefono = 655111222
    )

    val clienteMock2 = Cliente(
        idCliente = 2L,
        nombre = "María López",
        deuda = 0.0,
        telefono = 655333444
    )

    // 2. Mocks de los ítems en el carrito (vienen del TPV)
    val productoCartMock = Producto(1L, "Cera Mate", 5.0, 12.0, 10) // 12.0 €
    val servicioCartMock = Servicio(1L, "Corte Degradado", 15.0, "Corte a máquina", empleadoMock1) // 15.0 €

    // El total automático debería mostrar 27.00 €

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddSaleScreen(
                productosCart = listOf(productoCartMock),
                serviciosCart = listOf(servicioCartMock),

                empleadosDisponibles = listOf(empleadoMock1, empleadoMock2),
                clientesDisponibles = listOf(clienteMock1, clienteMock2),

                empleadoSeleccionado = empleadoMock1, // Simulamos que ya eligió uno
                clienteSeleccionado = null,           // Lo dejamos en null para ver el placeholder
                tipoPago = "Tarjeta",                 // Simulamos que seleccionó Tarjeta

                onEvent = {}, // Lambda vacía para la UI de preview
                onBack = {},
                onFacturaGuardada = {}
            )
        }
    }
}

