package com.proyecto.PeluPos.ui.features.tpv

import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.factura.FacturaRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TpvViewModel @Inject constructor(
    private val productoRepository: ProductoRepository,
    private val servicioRepository: ServicioRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    // ESTADOS
    private val _productosDisponibles = MutableStateFlow<List<Producto>>(emptyList())
    val productosDisponibles: StateFlow<List<Producto>> = _productosDisponibles.asStateFlow()

    private val _serviciosDisponibles = MutableStateFlow<List<Servicio>>(emptyList())
    val serviciosDisponibles: StateFlow<List<Servicio>> = _serviciosDisponibles.asStateFlow()

    val carritoServicio: StateFlow<List<Servicio>> = carritoRepository.serviciosEscogidos

    val carritoProducto: StateFlow<List<Producto>> = carritoRepository.productosEscogidos


    init {
        cargarCatalogos()
    }

    private fun cargarCatalogos() {
        viewModelScope.launch {
            _productosDisponibles.value = productoRepository.getProductos()
            _serviciosDisponibles.value = servicioRepository.getServicios()
        }
    }

    fun onEvent(event: TpvEvent) {
        when (event) {
            is TpvEvent.OnAgregarProducto -> {
                carritoRepository.agregarProducto(event.producto)

            }
            is TpvEvent.OnAgregarServicio -> {
                carritoRepository.agregarServicio(event.servicio)
            }
            TpvEvent.OnVaciarCarrito -> {
                carritoRepository.vaciarCarrito()
            }
        }
    }
}