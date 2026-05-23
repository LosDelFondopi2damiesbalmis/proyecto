package com.proyecto.PeluPos.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.proyecto.PeluPos.ui.features.products.ProductoFormScreen
import com.proyecto.PeluPos.ui.features.products.ProductosScreen
import com.proyecto.PeluPos.ui.features.products.ProductosViewModel

@Serializable
object ProductosListRoute

@Serializable
object ProductoFormRoute



fun NavGraphBuilder.productosDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit,
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
            navigateToForm = navigateToForm // Usamos esta función tanto para crear como para editar
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ProductoFormRoute> {
        val vm = hiltViewModel<ProductosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ProductoFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack // Al cancelar o guardar con éxito, volvemos atrás
        )
    }
}