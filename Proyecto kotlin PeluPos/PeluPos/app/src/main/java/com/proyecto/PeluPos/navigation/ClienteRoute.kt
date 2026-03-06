package com.proyecto.PeluPos.navigation

import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyecto.PeluPos.ui.features.clientes.ClienteDetailScreen
import com.proyecto.PeluPos.ui.features.clientes.ClienteFormScreen
import com.proyecto.PeluPos.ui.features.clientes.ClientesEvent
import com.proyecto.PeluPos.ui.features.clientes.ClientesScreen
import com.proyecto.PeluPos.ui.features.clientes.ClientesViewModel

@Serializable
object ClientesListRoute

@Serializable
object ClienteFormRoute

// Opcional: Si en el futuro vas a crear una pantalla de Detalle del Cliente
@Serializable
data class ClienteDetailRoute(val idCliente: Long)

fun NavGraphBuilder.clientesDestination(
    vm: ClientesViewModel,
    toggleSidebar: () -> Unit,
    navigateToForm: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE CLIENTES
    // ==========================================
    composable<ClientesListRoute> {
        val state by vm.uiState.collectAsStateWithLifecycle()

        ClientesScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToNewCliente = navigateToForm, // Usamos esta para crear
            navigateToClienteDetail = navigateToDetail // Para ver el detalle
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ClienteFormRoute> {
        val state by vm.uiState.collectAsStateWithLifecycle()

        ClienteFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack // Al guardar o cancelar, simplemente volvemos atrás
        )
    }
    // ==========================================
    // 3. DETALLE DEL CLIENTE
    // ==========================================
    composable<ClienteDetailRoute> { backStackEntry ->
        // 1. Extraemos el ID tipado de la ruta
        val route = backStackEntry.toRoute<ClienteDetailRoute>()

        // 2. Recolectamos el estado global del ViewModel de clientes
        val state by vm.uiState.collectAsStateWithLifecycle()

        // 3. Buscamos el cliente que coincida con el ID
        val clienteEncontrado = state.todosLosClientes.find { it.idCliente == route.idCliente }

        // 4. Se lo pasamos a nuestra pantalla stateless
        ClienteDetailScreen(
            cliente = clienteEncontrado,
            onBack = onBack,
            onEditClick = {
                // Preparamos la edición en el ViewModel y viajamos al formulario
                vm.onEvent(ClientesEvent.PrepararEdicion(route.idCliente))
                navigateToForm()
            },
            onPayDebtClick = {
                vm.onEvent(ClientesEvent.SaldarDeuda(route.idCliente))
            }
        )
    }
}