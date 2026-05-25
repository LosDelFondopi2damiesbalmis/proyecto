package com.proyecto.PeluPos.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.toRoute
import com.proyecto.PeluPos.ui.features.products.ProductoFormScreen
import com.proyecto.PeluPos.ui.features.products.ProductosScreen
import com.proyecto.PeluPos.ui.features.products.ProductosViewModel

// ==========================================
// RUTAS
// ==========================================
@Serializable
object ProductosListRoute

@Serializable
data class ProductoFormRoute(
    val idProducto: Long? = null // 👈 null = Crear nuevo, Número = Editar
)

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.productosDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: (Long?) -> Unit, // 👈 Ahora acepta el ID opcional
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE PRODUCTOS
    // ==========================================
    composable<ProductosListRoute> {
        val vm = hiltViewModel<ProductosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ProductosScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            // Pasamos la función directamente.
            // ⚠️ IMPORTANTE: Mira la nota de abajo sobre ProductosScreen
            navigateToForm = navigateToForm
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ProductoFormRoute> { backStackEntry ->
        // Extraemos la ruta para que Hilt haga su magia
        val routeData = backStackEntry.toRoute<ProductoFormRoute>()

        val vm = hiltViewModel<ProductosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ProductoFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack
        )
    }
}