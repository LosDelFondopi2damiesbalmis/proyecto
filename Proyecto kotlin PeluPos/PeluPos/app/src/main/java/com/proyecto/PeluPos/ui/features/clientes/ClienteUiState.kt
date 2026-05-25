package com.proyecto.PeluPos.ui.features.clientes

import com.proyecto.PeluPos.models.Cliente

data class ClientesUiState(
    // Lista completa y lista filtrada
    val todosLosClientes: List<Cliente> = emptyList(),
    val clientesVisibles: List<Cliente> = emptyList(),
    val searchQuery: String = "",

    // Datos del formulario
    val editandoClienteId: Long? = null,
    val formNombre: String = "",
    val formTelefono: String = "",
    val formDeuda: String = "", // Lo guardamos como String para el TextField

    // 🚀 NUEVO: Banderas de control de red para Retrofit
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = formNombre.isNotBlank()
}