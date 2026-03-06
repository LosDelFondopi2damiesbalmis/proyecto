package com.proyecto.PeluPos.ui.features.clientes

sealed interface ClientesEvent {
    object CargarClientes : ClientesEvent
    data class OnSearchQueryChange(val query: String) : ClientesEvent

    // Formulario
    object PrepararNuevoCliente : ClientesEvent
    data class PrepararEdicion(val idCliente: Long) : ClientesEvent
    data class OnNombreChange(val nombre: String) : ClientesEvent
    data class OnTelefonoChange(val telefono: String) : ClientesEvent
    data class OnDeudaChange(val deuda: String) : ClientesEvent
    data class SaldarDeuda(val idCliente: Long) : ClientesEvent
    object GuardarCliente : ClientesEvent
}