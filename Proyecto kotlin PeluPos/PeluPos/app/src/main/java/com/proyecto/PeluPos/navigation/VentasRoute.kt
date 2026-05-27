package com.proyecto.PeluPos.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.features.ventas.VentasScreen
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.toRoute
import com.proyecto.PeluPos.ui.features.ventas.AddSaleScreen
import com.proyecto.PeluPos.ui.features.ventas.DetallesVentaScreen
import com.proyecto.PeluPos.ui.features.ventas.FacturacionViewModel

// ==========================================
// RUTAS
// ==========================================
@Serializable
object VentasRoute

@Serializable
object AddSaleRoute

@Serializable
data class DetalleVentaRoute(val idFactura: Long) // 👈 Perfecto, ya lo tenías bien

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.salesDestination(
    toggleSidebar: () -> Unit,
    navigateToNewSale: () -> Unit,
    navigateToSaleDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    // 1. HISTORIAL DE VENTAS (Igual que lo tenías)
    composable<VentasRoute> {
        val vm = hiltViewModel<FacturacionViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        VentasScreen(
            facturas = state.facturasVisibles,
            searchQuery = state.searchQuery,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToNewSale = navigateToNewSale,
            navigateToSaleDetail = navigateToSaleDetail,
            onBack = onBack
        )
    }

    // 2. CREAR NUEVA FACTURA (Igual que lo tenías)
    composable<AddSaleRoute> {
        val vm = hiltViewModel<FacturacionViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        AddSaleScreen(
            // ... todos tus parámetros ...
            productosCart = state.carritoProductos,
            serviciosCart = state.carritoServicios,
            empleadosDisponibles = state.empleadosDisponibles,
            clientesDisponibles = state.clientesDisponibles,
            empleadoSeleccionado = state.empleadoSeleccionado,
            clienteSeleccionado = state.clienteSeleccionado,
            tipoPago = state.tipoPago,
            onEvent = vm::onEvent,
            onBack = onBack,
            onFacturaGuardada = onBack
        )
    }

    // 3. DETALLE DE LA VENTA
    composable<DetalleVentaRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<DetalleVentaRoute>()
        val vm = hiltViewModel<FacturacionViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        // ⚠️ ATENCIÓN AQUÍ: Si el ViewModel acaba de nacer, todasLasFacturas puede estar
        // vacía unos instantes. Tu DetallesVentaScreen debe soportar que le llegue 'null'
        // y mostrar un "Cargando..." mientras tanto.
        val facturaEncontrada = state.todasLasFacturas.find { it.idFactura == route.idFactura }

        DetallesVentaScreen(
            factura = facturaEncontrada,
            onBack = onBack,
            onEvent = { event -> vm.onEvent(event) }
        )
    }
}