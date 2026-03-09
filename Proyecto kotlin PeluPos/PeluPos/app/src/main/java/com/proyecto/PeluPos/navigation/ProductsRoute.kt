package com.proyecto.PeluPos.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.proyecto.PeluPos.ui.features.products.ProductoFormScreen
import com.proyecto.PeluPos.ui.features.products.ProductosScreen
import com.proyecto.PeluPos.ui.features.products.ProductosViewModel

@Serializable
object ProductosListRoute

@Serializable
object ProductoFormRoute



fun NavGraphBuilder.productosDestination(
    vm: ProductosViewModel,
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE PRODUCTOS
    // ==========================================
    composable<ProductosListRoute> {
        val state by vm.uiState.collectAsStateWithLifecycle()

        ProductosScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToForm = navigateToForm // Usamos esta función tanto para crear como para editar
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ProductoFormRoute> {
        val state by vm.uiState.collectAsStateWithLifecycle()

        ProductoFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack // Al cancelar o guardar con éxito, volvemos atrás
        )
    }
}