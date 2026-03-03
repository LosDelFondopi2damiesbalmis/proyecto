package com.proyecto.PeluPos.ui.products


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.ui.products.Product
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductScreen(
    onBackClick: () -> Unit,
    onSaveProduct: (Product) -> Unit
) {
    // Estados para los campos del formulario
    var productName by remember { mutableStateOf("") }
    var productCode by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Champús") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var minStock by remember { mutableStateOf("") }
    var supplier by remember { mutableStateOf("") }

    var showCategoryMenu by remember { mutableStateOf(false) }

    val categories = listOf("Champús", "Tintes", "Mascarillas", "Fijadores", "Herramientas", "Otros")
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Nuevo Producto",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Validar y guardar producto
                            if (productName.isNotEmpty() && productCode.isNotEmpty() && price.isNotEmpty()) {
                                val newProduct = Product(
                                    id = (System.currentTimeMillis() + Random.nextInt(1000)).toString(),
                                    name = productName,
                                    code = productCode,
                                    category = category,
                                    price = price.toDoubleOrNull() ?: 0.0,
                                    stock = stock.toIntOrNull() ?: 0,
                                    minStock = minStock.toIntOrNull() ?: 5,
                                    supplier = supplier
                                )
                                onSaveProduct(newProduct)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo Nombre
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Nombre del producto *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = productName.isEmpty()
            )

            // Campo Código
            OutlinedTextField(
                value = productCode,
                onValueChange = { productCode = it },
                label = { Text("Código del producto *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = productCode.isEmpty()
            )

            // Selector de categoría
            ExposedDropdownMenuBox(
                expanded = showCategoryMenu,
                onExpandedChange = { showCategoryMenu = !showCategoryMenu }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    label = { Text("Categoría *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoryMenu)
                    }
                )

                ExposedDropdownMenu(
                    expanded = showCategoryMenu,
                    onDismissRequest = { showCategoryMenu = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                category = cat
                                showCategoryMenu = false
                            }
                        )
                    }
                }
            }

            // Fila: Precio y Stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio (€) *") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = price.isEmpty() || price.toDoubleOrNull() == null
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock inicial") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    placeholder = { Text("0") }
                )
            }

            // Stock mínimo
            OutlinedTextField(
                value = minStock,
                onValueChange = { minStock = it },
                label = { Text("Stock mínimo de alerta") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Recomendado: 5") }
            )

            // Proveedor
            OutlinedTextField(
                value = supplier,
                onValueChange = { supplier = it },
                label = { Text("Proveedor") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Botón de guardar
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (productName.isNotEmpty() && productCode.isNotEmpty() && price.isNotEmpty()) {
                        val newProduct = Product(
                            id = (System.currentTimeMillis() + Random.nextInt(1000)).toString(),
                            name = productName,
                            code = productCode,
                            category = category,
                            price = price.toDoubleOrNull() ?: 0.0,
                            stock = stock.toIntOrNull() ?: 0,
                            minStock = minStock.toIntOrNull() ?: 5,
                            supplier = supplier
                        )
                        onSaveProduct(newProduct)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = productName.isNotEmpty() && productCode.isNotEmpty() && price.isNotEmpty()
            ) {
                Text("Guardar Producto", fontSize = 16.sp)
            }

            // Texto de campos requeridos
            Text(
                text = "* Campos obligatorios",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun NewProductScreenPreview() {
    MaterialTheme {
        NewProductScreen(
            onBackClick = {},
            onSaveProduct = {}
        )
    }
}