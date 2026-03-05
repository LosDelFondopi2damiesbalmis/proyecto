package com.proyecto.PeluPos.ui.features.tpv

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
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TpvScreen(
    // Estados pasados directamente
    productosDisponibles: List<Producto>,
    serviciosDisponibles: List<Servicio>,
    carritoProductos: List<Producto>,
    carritoServicios: List<Servicio>,
    // Eventos de la UI
    onEvent: (TpvEvent) -> Unit,
    // Navegación (callbacks)
    toggleSidebar: () -> Unit,
    navigateToSales: () -> Unit,
    navigateToCreateInvoice: (productos: List<Producto>, servicios: List<Servicio>) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Productos", "Servicios")

    // Cálculos derivados del estado
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
            if (totalItems > 0) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
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
                if (selectedTab == 0) {
                    items(productosDisponibles) { producto ->
                        ItemCard(
                            nombre = producto.nombre,
                            precio = producto.precioVenta,
                            // Emitimos el evento en lugar de llamar al ViewModel
                            onClick = { onEvent(TpvEvent.OnAgregarProducto(producto)) }
                        )
                    }
                } else {
                    items(serviciosDisponibles) { servicio ->
                        ItemCard(
                            nombre = servicio.nombre,
                            precio = servicio.precio,
                            // Emitimos el evento en lugar de llamar al ViewModel
                            onClick = { onEvent(TpvEvent.OnAgregarServicio(servicio)) }
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



@Preview(
    showBackground = true,
    device = "id:pixel_5",
    name = "Pantalla TPV - MVI"
)
@Composable
fun TpvScreenPreview() {
    // 1. Simulamos el Empleado (necesario para el Servicio)
    val empleadoMock = Empleado(
        idEmpleado = 1L,
        nombre = "Carlos",
        email = "carlos@pelupos.com",
        telefono = 600123456,
        cargo = "Peluquero"
    )

    // 2. Simulamos la lista de Productos disponibles
    val mockProductos = listOf(
        Producto(
            idProducto = 1L,
            nombre = "Champú Kerastase",
            precioCompra = 10.00,
            precioVenta = 24.99,
            stock = 15
        ),
        Producto(
            idProducto = 2L,
            nombre = "Tinte Wella",
            precioCompra = 5.50,
            precioVenta = 12.50,
            stock = 8
        ),
        Producto(
            idProducto = 3L,
            nombre = "Mascarilla Hidratante",
            precioCompra = 8.00,
            precioVenta = 18.75,
            stock = 5
        )
    )

    // 3. Simulamos la lista de Servicios disponibles
    val mockServicios = listOf(
        Servicio(
            idServicio = 1L,
            nombre = "Corte Caballero",
            precio = 15.00,
            descripcion = "Corte clásico a tijera y máquina",
            empleado = empleadoMock
        ),
        Servicio(
            idServicio = 2L,
            nombre = "Tinte Completo",
            precio = 40.00,
            descripcion = "Tinte de raíz a puntas",
            empleado = empleadoMock
        ),
        Servicio(
            idServicio = 3L,
            nombre = "Corte + Secado",
            precio = 25.00,
            descripcion = "Corte y peinado final",
            empleado = empleadoMock
        )
    )

    // 4. Simulamos un carrito con 1 producto y 1 servicio para ver el BottomBar
    val mockCarritoProductos = listOf(mockProductos[0]) // 1 Champú (24.99)
    val mockCarritoServicios = listOf(mockServicios[0]) // 1 Corte Caballero (15.00)

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TpvScreen(
                productosDisponibles = mockProductos,
                serviciosDisponibles = mockServicios,
                carritoProductos = mockCarritoProductos,
                carritoServicios = mockCarritoServicios,
                onEvent = { /* No hacemos nada en la preview */ },
                toggleSidebar = { },
                navigateToSales = { },
                navigateToCreateInvoice = { _, _ -> }
            )
        }
    }
}