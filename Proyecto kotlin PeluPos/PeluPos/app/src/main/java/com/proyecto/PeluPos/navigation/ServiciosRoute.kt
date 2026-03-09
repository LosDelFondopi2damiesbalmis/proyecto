package com.proyecto.PeluPos.navigation

import ServiciosScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.features.servicios.ServicioFormScreen
import com.proyecto.PeluPos.ui.features.servicios.ServiciosViewModel

@Serializable
object ServiciosListRoute

@Serializable
object ServicioFormRoute


fun NavGraphBuilder.serviciosDestination(
    vm: ServiciosViewModel,
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE SERVICIOS
    // ==========================================
    composable<ServiciosListRoute> {
        val state by vm.uiState.collectAsStateWithLifecycle()

        ServiciosScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToForm = navigateToForm // Se usa tanto al darle a "Añadir" como al hacer clic en una tarjeta
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ServicioFormRoute> {
        val state by vm.uiState.collectAsStateWithLifecycle()

        ServicioFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack // Vuelve a la lista al guardar o cancelar
        )
    }
}