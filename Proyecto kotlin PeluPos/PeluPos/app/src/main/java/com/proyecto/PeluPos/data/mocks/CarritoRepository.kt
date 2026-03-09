package com.proyecto.PeluPos.data.mocks

import com.proyecto.PeluPos.models.Producto
import com.proyecto.PeluPos.models.Servicio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarritoRepository @Inject constructor() {

    private val _productosEscogidos = MutableStateFlow<List<Producto>>(emptyList())
    val productosEscogidos: StateFlow<List<Producto>> = _productosEscogidos.asStateFlow()

    private val _serviciosEscogidos = MutableStateFlow<List<Servicio>>(emptyList())
    val serviciosEscogidos: StateFlow<List<Servicio>> = _serviciosEscogidos.asStateFlow()

    fun agregarProducto(producto: Producto) {
        _productosEscogidos.update { actual -> actual + producto }
    }

    fun agregarServicio(servicio: Servicio) {
        _serviciosEscogidos.update { actual -> actual + servicio }
    }

    fun vaciarCarrito() {
        _productosEscogidos.value = emptyList()
        _serviciosEscogidos.value = emptyList()
    }
}