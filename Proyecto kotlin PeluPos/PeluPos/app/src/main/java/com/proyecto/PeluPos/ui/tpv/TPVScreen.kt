package com.proyecto.PeluPos.ui.tpv

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Estructura temporal para el ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Estructura para el ticket
data class CartItem(val name: String, val price: Double, var quantity: Int = 1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpvScreen(
    toggleSidebar: () -> Unit,
    navigateToSales: () -> Unit
) {
    // Estados
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Productos", "Servicios")
    val cartItems = remember { mutableStateListOf<CartItem>() }
    var showCart by remember { mutableStateOf(false) } // Controla si vemos el catálogo o el ticket

    val totalAmount = cartItems.sumOf { it.price * it.quantity }
    val totalItems = cartItems.sumOf { it.quantity }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (showCart) "Ticket Actual" else "TPV", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    if (showCart) {
                        IconButton(onClick = { showCart = false }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                actions = {
                    if (!showCart) {
                        TextButton(onClick = navigateToSales) {
                            Text("Historial")
                        }
                    }
                }
            )
        },
        bottomBar = {
            // Barra inferior flotante para ir al ticket (solo visible en el catálogo si hay items)
            if (!showCart && cartItems.isNotEmpty()) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth().clickable { showCart = true },
                    tonalElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("$totalItems items", fontWeight = FontWeight.Medium)
                        }
                        Text(String.format("%.2f €", totalAmount), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    ) { paddingValues ->

        if (showCart) {
            // ==========================================
            // VISTA 2: TICKET / CARRITO
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Nodo: "Muestra Usuario Activo"
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Usuario", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Atendido por", fontSize = 12.sp, color = Color.Gray)
                        Text("Laura Gómez", fontWeight = FontWeight.Bold)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Lista de productos en el ticket
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { item ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.name, fontWeight = FontWeight.Medium)
                                Text("${item.quantity} x ${item.price} €", fontSize = 14.sp, color = Color.Gray)
                            }
                            Text(String.format("%.2f €", item.price * item.quantity), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                // Total y Botón de Cobro
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("TOTAL", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(String.format("%.2f €", totalAmount), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }

                // Nodo: "Crea Factura Nueva"
                Button(
                    onClick = {
                        cartItems.clear()
                        showCart = false // Volvemos al catálogo tras cobrar
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Cobrar e Imprimir Factura", fontSize = 16.sp)
                }
            }

        } else {
            // ==========================================
            // VISTA 1: CATÁLOGO
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 columnas fijas para móvil
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    val items = if (selectedTab == 0) {
                        listOf("Champú Kerastase" to 24.99, "Tinte Wella" to 12.50, "Mascarilla" to 18.75)
                    } else {
                        listOf("Corte Caballero" to 15.00, "Corte + Secado" to 25.00, "Tinte Completo" to 40.00)
                    }

                    items(items) { (name, price) ->
                        Card(
                            modifier = Modifier
                                .height(120.dp)
                                .clickable {
                                    val existing = cartItems.find { it.name == name }
                                    if (existing != null) {
                                        existing.quantity++
                                        val index = cartItems.indexOf(existing)
                                        cartItems[index] = existing.copy(quantity = existing.quantity)
                                    } else {
                                        cartItems.add(CartItem(name, price))
                                    }
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(12.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = name, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "${price} €", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Preview(
    showBackground = true,
    device = "id:pixel_5", // Simula un teléfono móvil estándar
    name = "Pantalla TPV (Móvil)"
)
@Composable
fun TpvScreenMobilePreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TpvScreen(
                toggleSidebar = { },
                navigateToSales = { }
            )
        }
    }
}