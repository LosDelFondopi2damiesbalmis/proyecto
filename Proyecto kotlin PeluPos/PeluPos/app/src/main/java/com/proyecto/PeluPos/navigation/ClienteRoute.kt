package com.proyecto.PeluPos.navigation

import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.proyecto.PeluPos.ui.features.clientes.ClienteDetailScreen
import com.proyecto.PeluPos.ui.features.clientes.ClienteFormScreen
import com.proyecto.PeluPos.ui.features.clientes.ClientesEvent
import com.proyecto.PeluPos.ui.features.clientes.ClientesScreen
import com.proyecto.PeluPos.ui.features.clientes.ClientesViewModel


// ==========================================
// RUTAS
// ==========================================
@Serializable
object ClientesListRoute

@Serializable
data class ClienteFormRoute(
    val idCliente: Long? = null // 👈 null = Crear, Número = Editar
)

@Serializable
data class ClienteDetailRoute(val idCliente: Long)

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.clientesDestination(
    toggleSidebar: () -> Unit,
    navigateToForm: (Long?) -> Unit, // 👈 Ahora acepta el ID opcional
    navigateToDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. LISTA DE CLIENTES
    // ==========================================
    composable<ClientesListRoute> {
        val vm = hiltViewModel<ClientesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ClientesScreen(
            state = state,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            // 🚀 CREAR: Le pasamos 'null' para que abra un formulario vacío
            navigateToNewCliente = { navigateToForm(null) },
            navigateToClienteDetail = navigateToDetail
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<ClienteFormRoute> { backStackEntry ->
        // Extraemos la ruta (aunque Hilt lo leerá por detrás)
        val routeData = backStackEntry.toRoute<ClienteFormRoute>()

        val vm = hiltViewModel<ClientesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        ClienteFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onNavigateBack = onBack
        )
    }

    // ==========================================
    // 3. DETALLE DEL CLIENTE
    // ==========================================
    composable<ClienteDetailRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ClienteDetailRoute>()
        val vm = hiltViewModel<ClientesViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        // ⚠️ NOTA: Esto asume que el ViewModel del detalle carga todos los clientes
        // nada más nacer. Si no es así, el clienteEncontrado será null.
        val clienteEncontrado = state.todosLosClientes.find { it.idCliente == route.idCliente }

        ClienteDetailScreen(
            cliente = clienteEncontrado,
            onBack = onBack,
            onEditClick = {
                navigateToForm(route.idCliente)
            },
            onPayDebtClick = {
                vm.onEvent(ClientesEvent.SaldarDeuda(route.idCliente))
            }
        )
    }
}