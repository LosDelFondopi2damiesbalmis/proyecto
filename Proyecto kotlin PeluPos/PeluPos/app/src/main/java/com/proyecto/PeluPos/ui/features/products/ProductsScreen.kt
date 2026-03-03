package com.proyecto.PeluPos.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.ui.theme.PeluposLightSuccess
import com.proyecto.PeluPos.ui.theme.PeluposLightError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    toggleSidebar: () -> Unit,
    navigateToProductDetail: (productId: String) -> Unit,
    navigateToNewProduct: () -> Unit  // Añadido para navegar al formulario
) {
    // Estado para la búsqueda y lista de productos
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Champús", "Tintes", "Mascarillas", "Fijadores", "Herramientas")

    // Datos de ejemplo
    val products = remember {
        listOf(
            Product(
                id = "1",
                name = "Champú Reparador Kerastase",
                code = "CH-KERA-001",
                category = "Champús",
                price = 24.99,
                stock = 15,
                minStock = 5,
                supplier = "L'Oréal Professional"
            ),
            Product(
                id = "2",
                name = "Tinte Wella Koleston",
                code = "TINT-WELLA-55",
                category = "Tintes",
                price = 12.50,
                stock = 3,
                minStock = 10,
                supplier = "Wella Professionals"
            ),
            Product(
                id = "3",
                name = "Mascarilla Hidratante",
                code = "MASC-HIDRA-02",
                category = "Mascarillas",
                price = 18.75,
                stock = 8,
                minStock = 6,
                supplier = "Schwarzkopf"
            ),
            Product(
                id = "4",
                name = "Laca Fijación Extra Fuerte",
                code = "LACA-EXTRA-01",
                category = "Fijadores",
                price = 9.99,
                stock = 22,
                minStock = 8,
                supplier = "Taft"
            ),
            Product(
                id = "5",
                name = "Tijeras Profesionales Jaguar",
                code = "TIJ-JAG-7",
                category = "Herramientas",
                price = 89.99,
                stock = 2,
                minStock = 3,
                supplier = "Jaguar"
            )
        )
    }

    // Filtrar productos
    val filteredProducts = products.filter { product ->
        (selectedCategory == "Todos" || product.category == selectedCategory) &&
                (searchQuery.isEmpty() ||
                        product.name.contains(searchQuery, ignoreCase = true) ||
                        product.code.contains(searchQuery, ignoreCase = true))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // --- CABECERA ---
        ProductsHeader(
            toggleSidebar = toggleSidebar,
            onAddProduct = navigateToNewProduct,  // Usando el parámetro
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            selectedCategory = selectedCategory,
            categories = categories,
            onCategorySelected = { selectedCategory = it },
            totalProducts = filteredProducts.size,
            lowStockCount = filteredProducts.count { it.stock < it.minStock },
            totalValue = filteredProducts.sumOf { it.price }.toInt()
        )

        // --- CONTENIDO ---
        if (filteredProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isNotEmpty())
                        "No hay productos que coincidan con '$searchQuery'"
                    else
                        "No hay productos en esta categoría",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onClick = { navigateToProductDetail(product.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

// --- COMPONENTE CABECERA CORREGIDO ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsHeader(
    toggleSidebar: () -> Unit,
    onAddProduct: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: String,
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    totalProducts: Int,
    lowStockCount: Int,
    totalValue: Int
) {
    Column {
        // Fila superior: Título y botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        "Gestión de Productos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "Catálogo y control de stock",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Botón Añadir Producto - CORREGIDO
            FilledTonalButton(
                onClick = onAddProduct,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary

                ),

                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Añadir producto",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Nuevo")
            }
        }

        // Barra de búsqueda - IMPLEMENTACIÓN CORRECTA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar por nombre o código...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
            )
        }

        // Filtros por categoría (Chips) - SCROLL HORIZONTAL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        // Resumen estadístico
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SummaryItem("Total", "$totalProducts")
            SummaryItem("Stock bajo", "$lowStockCount")
            SummaryItem("Valor", "${totalValue}€")
        }

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    }


@Composable
fun SummaryItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// --- MODELO DE DATOS ---
data class Product(
    val id: String,
    val name: String,
    val code: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val minStock: Int,
    val supplier: String,
    val lastRestock: String? = null
)

// --- COMPONENTE PRODUCT CARD CORREGIDO ---
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Información principal
            Column(modifier = Modifier.weight(1f)) {
                // Nombre y categoría
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    // Badge de categoría
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            product.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Código y proveedor
                Text(
                    "Código: ${product.code}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "Proveedor: ${product.supplier}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información lateral
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Precio
                Text(
                    "${String.format("%.2f", product.price)} €",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Indicador de stock
                StockIndicator(
                    stock = product.stock,
                    minStock = product.minStock
                )
            }
        }
    }
}

@Composable
fun StockIndicator(stock: Int, minStock: Int) {
    val (backgroundColor, textColor, text) = when {
        stock == 0 -> Triple(
            PeluposLightError.copy(alpha = 0.2f),
            PeluposLightError,
            "AGOTADO"
        )
        stock < minStock -> Triple(
            PeluposLightError.copy(alpha = 0.2f),
            PeluposLightError,
            "BAJO: $stock"
        )
        stock < minStock * 2 -> Triple(
            Color(0xFFFFB347).copy(alpha = 0.2f),
            Color(0xFFFFB347),
            "$stock uds"
        )
        else -> Triple(
            PeluposLightSuccess.copy(alpha = 0.2f),
            PeluposLightSuccess,
            "$stock uds"
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

// --- PREVIEWS ACTUALIZADOS ---
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun ProductsScreenPreview() {
    MaterialTheme {
        ProductsScreen(
            toggleSidebar = {},
            navigateToProductDetail = {},
            navigateToNewProduct = {}
        )
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    MaterialTheme {
        ProductCard(
            product = Product(
                id = "1",
                name = "Champú Reparador Kerastase",
                code = "CH-KERA-001",
                category = "Champús",
                price = 24.99,
                stock = 3,
                minStock = 5,
                supplier = "L'Oréal Professional"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
fun ProductsHeaderPreview() {
    MaterialTheme {
        ProductsHeader(
            toggleSidebar = {},
            onAddProduct = {},
            searchQuery = "",
            onSearchQueryChange = {},
            selectedCategory = "Todos",
            categories = listOf("Todos", "Champús", "Tintes"),
            onCategorySelected = {},
            totalProducts = 5,
            lowStockCount = 2,
            totalValue = 157
        )
    }
}