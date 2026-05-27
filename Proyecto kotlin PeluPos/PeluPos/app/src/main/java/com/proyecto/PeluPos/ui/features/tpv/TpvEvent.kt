package com.proyecto.PeluPos.ui.features.tpv

import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio

sealed interface TpvEvent {
    data class OnAgregarProducto(val producto: Producto) : TpvEvent
    data class OnAgregarServicio(val servicio: Servicio) : TpvEvent
    object OnLimpiarCarrito : TpvEvent
    object OnVaciarCarrito : TpvEvent
}