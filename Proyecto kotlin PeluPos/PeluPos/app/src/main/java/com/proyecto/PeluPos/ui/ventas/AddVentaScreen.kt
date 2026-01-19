package com.proyecto.PeluPos.ui.ventas

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
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSaleScreen(
    onBack: () -> Unit,
    onSave: (Map<String, Any>) -> Unit // Devuelve el mapa listo para tu lista
) {
    // --- ESTADOS DEL FORMULARIO ---
    var servicio by remember { mutableStateOf("") }
    var precioTexto by remember { mutableStateOf("") }
    var empleado by remember { mutableStateOf("Carlos") } // Valor por defecto
    var metodoPago by remember { mutableStateOf("Efectivo") }

    // Estado para el Dropdown de empleados
    var expandedEmpleado by remember { mutableStateOf(false) }
    val empleadosList = listOf("Carlos", "Elena", "Pedro", "Admin")

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo Ticket", fontWeight = FontWeight.Bold) },
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
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 1. SECCIÓN: EMPLEADO (Dropdown)
            Text("¿Quién atiende?", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            ExposedDropdownMenuBox(
                expanded = expandedEmpleado,
                onExpandedChange = { expandedEmpleado = !expandedEmpleado }
            ) {
                OutlinedTextField(
                    value = empleado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Empleado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEmpleado) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedEmpleado,
                    onDismissRequest = { expandedEmpleado = false }
                ) {
                    empleadosList.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                empleado = item
                                expandedEmpleado = false
                            }
                        )
                    }
                }
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // 2. SECCIÓN: DETALLES DEL SERVICIO
            Text("Detalles de la venta", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            OutlinedTextField(
                value = servicio,
                onValueChange = { servicio = it },
                label = { Text("Servicio o Producto") },
                placeholder = { Text("Ej: Corte Caballero") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.ContentCut, null) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences, imeAction = ImeAction.Next),
                singleLine = true
            )

            OutlinedTextField(
                value = precioTexto,
                onValueChange = {
                    // Validación simple para permitir solo números y puntos decimales
                    if (it.all { char -> char.isDigit() || char == '.' }) {
                        precioTexto = it
                    }
                },
                label = { Text("Precio (€)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Euro, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                singleLine = true
            )

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // 3. SECCIÓN: MÉTODO DE PAGO
            Text("Método de Pago", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PaymentOptionButton(
                    text = "Efectivo",
                    icon = Icons.Default.AttachMoney,
                    isSelected = metodoPago == "Efectivo",
                    onClick = { metodoPago = "Efectivo" },
                    modifier = Modifier.weight(1f)
                )
                PaymentOptionButton(
                    text = "Tarjeta",
                    icon = Icons.Default.CreditCard,
                    isSelected = metodoPago == "Tarjeta",
                    onClick = { metodoPago = "Tarjeta" },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja los botones al final si hay espacio

            // 4. BOTONES DE ACCIÓN
            Button(
                onClick = {
                    if (servicio.isNotBlank() && precioTexto.isNotBlank()) {
                        // Construimos el Mapa tal como lo usa tu VentasScreen
                        val nuevaVenta = mapOf(
                            "id" to "V-${System.currentTimeMillis().toString().takeLast(4)}", // ID aleatorio simple
                            "servicio" to servicio,
                            "fecha" to "Hoy, ${getCurrentTime()}", // Función helper abajo
                            "precio" to (precioTexto.toDoubleOrNull() ?: 0.0),
                            "metodo" to metodoPago,
                            "empleado" to empleado
                        )
                        onSave(nuevaVenta)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = servicio.isNotBlank() && precioTexto.isNotBlank() // Deshabilitado si está vacío
            ) {
                Text("Guardar Ticket", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- COMPONENTES AUXILIARES PARA ESTA PANTALLA ---

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


@Preview
@Composable
fun PreviewAddSale() {
    // Usamos el tema que definimos antes para ver los colores reales
    PeluPosTheme(darkTheme = false) {
        AddSaleScreen(
            onBack = {},
            onSave = {}
        )
    }
}
