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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpvScreen(
    // Recibimos los catálogos reales desde el ViewModel o nivel superior
    productosDisponibles: List<Producto>,
    serviciosDisponibles: List<Servicio>,
    toggleSidebar: () -> Unit,
    navigateToSales: () -> Unit,
    // Pasamos las listas reales a la pantalla de crear factura
    navigateToCreateInvoice: (productos: List<Producto>, servicios: List<Servicio>) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Productos", "Servicios")

    // ESTADO DEL CARRITO: Ahora guarda objetos reales en lugar de Strings
    // Como tu modelo Factura pide MutableList, iremos añadiendo los items a estas listas
    val carritoProductos = remember { mutableStateListOf<Producto>() }
    val carritoServicios = remember { mutableStateListOf<Servicio>() }

    // Cálculos derivados
    val totalAmount = carritoProductos.sumOf { it.precioVenta } + carritoServicios.sumOf { it.precio }
    val totalItems = carritoProductos.size + carritoServicios.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TPV", fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = navigateToSales) {
                        Text("Historial")
                    }
                }
            )
        },
        bottomBar = {
            // Barra inferior flotante: Ahora nos lleva a la pantalla de crear factura
            if (totalItems > 0) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Navegamos pasando las listas actuales
                            navigateToCreateInvoice(carritoProductos, carritoServicios)
                        },
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
        // ==========================================
        // CATÁLOGO DE SELECCIÓN
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
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Dibujamos Productos o Servicios según la pestaña
                if (selectedTab == 0) {
                    items(productosDisponibles) { producto ->
                        ItemCard(
                            nombre = producto.nombre,
                            precio = producto.precioVenta,
                            onClick = { carritoProductos.add(producto) } // Añadimos el objeto real
                        )
                    }
                } else {
                    items(serviciosDisponibles) { servicio ->
                        ItemCard(
                            nombre = servicio.nombre,
                            precio = servicio.precio,
                            onClick = { carritoServicios.add(servicio) } // Añadimos el objeto real
                        )
                    }
                }
            }
        }
    }
}

// Componente extraído para no repetir código entre productos y servicios
@Composable
fun ItemCard(nombre: String, precio: Double, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(120.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = nombre, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${precio} €", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}