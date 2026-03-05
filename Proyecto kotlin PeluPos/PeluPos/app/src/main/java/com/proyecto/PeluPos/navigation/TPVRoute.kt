package com.proyecto.PeluPos.navigation
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio
import com.proyecto.PeluPos.ui.features.tpv.TpvScreen
import com.proyecto.PeluPos.ui.features.tpv.TpvViewModel

@Serializable
object TpvHomeRoute



fun NavGraphBuilder.tpvDestination(
    vm: TpvViewModel,
    toggleSidebar: () -> Unit,
    navigateToSales: () -> Unit,
    // Fíjate que en la pantalla original pasábamos las listas,
    // pero aquí en el enrutador solo nos importa la acción de navegar
    navigateToCreateInvoice: (List<Producto>, List<Servicio>) -> Unit
) {
    composable<TpvHomeRoute> {
        // Recolectamos el estado del ViewModel para pasárselo a la UI
        val productosDisponibles by vm.productosDisponibles.collectAsStateWithLifecycle()
        val serviciosDisponibles by vm.serviciosDisponibles.collectAsStateWithLifecycle()
        val carritoProductos by vm.carritoProducto.collectAsStateWithLifecycle()
        val carritoServicios by vm.carritoServicio.collectAsStateWithLifecycle()

        TpvScreen(
            productosDisponibles = productosDisponibles,
            serviciosDisponibles = serviciosDisponibles,
            carritoProductos = carritoProductos,
            carritoServicios = carritoServicios,
            onEvent = vm::onEvent,
            toggleSidebar = toggleSidebar,
            navigateToSales = navigateToSales,
            navigateToCreateInvoice = navigateToCreateInvoice
        )
    }
}