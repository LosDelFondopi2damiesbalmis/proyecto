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
@Serializable
object VentasRoute

@Serializable
object AddSaleRoute

@Serializable
data class DetalleVentaRoute(val idFactura: Long)


fun NavGraphBuilder.salesDestination(
    toggleSidebar: () -> Unit,
    navigateToNewSale: () -> Unit,
    navigateToSaleDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    // ==========================================
    // 1. HISTORIAL DE VENTAS
    // ==========================================
    composable<VentasRoute> {

        val vm = hiltViewModel<FacturacionViewModel>()
        // Recolectamos el estado una sola vez
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

    // ==========================================
    // 2. CREAR NUEVA FACTURA / CERRAR TICKET
    // ==========================================
    composable<AddSaleRoute> {
        val vm = hiltViewModel<FacturacionViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        AddSaleScreen(
            productosCart = state.carritoProductos,
            serviciosCart = state.carritoServicios,
            empleadosDisponibles = state.empleadosDisponibles,
            clientesDisponibles = state.clientesDisponibles,
            empleadoSeleccionado = state.empleadoSeleccionado,
            clienteSeleccionado = state.clienteSeleccionado,
            tipoPago = state.tipoPago,
            onEvent = vm::onEvent,
            onBack = onBack,
            onFacturaGuardada = onBack // Al guardar, simplemente volvemos atrás
        )
    }

    // ==========================================
    // 3. DETALLE DE LA VENTA
    // ==========================================
    composable<DetalleVentaRoute> { backStackEntry ->
        // 1. Extraemos el ID tipado de la ruta
        val route = backStackEntry.toRoute<DetalleVentaRoute>()
        val vm = hiltViewModel<FacturacionViewModel>()
        // 2. Recolectamos el estado global del ViewModel
        val state by vm.uiState.collectAsStateWithLifecycle()

        // 3. Buscamos la factura en nuestra lista (cache) que coincida con ese ID
        val facturaEncontrada = state.todasLasFacturas.find { it.idFactura == route.idFactura }

        // 4. Se la pasamos ya montada a la pantalla
        DetallesVentaScreen(
            factura = facturaEncontrada,
            onBack = onBack
        )
    }
}