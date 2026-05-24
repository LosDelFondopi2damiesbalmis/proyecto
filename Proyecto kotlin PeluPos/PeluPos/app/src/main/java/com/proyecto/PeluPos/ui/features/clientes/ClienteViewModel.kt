package com.proyecto.PeluPos.ui.features.clientes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.proyecto.PeluPos.data.mocks.cliente.ClienteRepository
import com.proyecto.PeluPos.models.Cliente
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.filter
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.proyecto.PeluPos.navigation.ClienteFormRoute
import kotlinx.coroutines.launch


@HiltViewModel
class ClientesViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository,
    savedStateHandle: SavedStateHandle // 🚀 1. INYECTAMOS LA ANTENA
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientesUiState())
    val uiState: StateFlow<ClientesUiState> = _uiState.asStateFlow()

    // 🚀 2. CAPTURAMOS EL ID DE LA RUTA (ClienteFormRoute)
    private val idClienteAEditar = savedStateHandle.toRoute<ClienteFormRoute>().idCliente

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val clientes = clienteRepository.getClientes()

                _uiState.update { state ->
                    // Creamos el nuevo estado base
                    var newState = state.copy(
                        todosLosClientes = clientes,
                        clientesVisibles = filtrarClientes(clientes, state.searchQuery),
                        isLoading = false
                    )
                    idClienteAEditar?.let { id ->
                        val cli = clientes.find { it.idCliente == id }
                        if (cli != null) {
                            newState = newState.copy(
                                editandoClienteId = cli.idCliente,
                                formNombre = cli.nombre ?: "",
                                formTelefono = if (cli.telefono == 0L || cli.telefono == null) "" else cli.telefono.toString(),
                                formDeuda = cli.deuda?.toString() ?: "0.0"
                            )
                        }
                    }
                    newState
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error: ${e.message}") }
            }
        }
    }

    private fun filtrarClientes(lista: List<Cliente>, query: String): List<Cliente> {
        if (query.isBlank()) return lista
        return lista.filter { (it.nombre ?: "").contains(query, ignoreCase = true) }
    }

    fun onEvent(event: ClientesEvent) {
        when (event) {
            ClientesEvent.CargarClientes -> cargarDatos()

            is ClientesEvent.OnSearchQueryChange -> {
                _uiState.update {
                    it.copy(
                        searchQuery = event.query,
                        clientesVisibles = filtrarClientes(it.todosLosClientes, event.query)
                    )
                }
            }

            // 🧹 EVENTOS ZOMBIS: Ya no se necesitan desde la UI, el init se encarga
            ClientesEvent.PrepararNuevoCliente -> { }
            is ClientesEvent.PrepararEdicion -> { }

            is ClientesEvent.OnNombreChange -> _uiState.update { it.copy(formNombre = event.nombre) }
            is ClientesEvent.OnTelefonoChange -> {
                if (event.telefono.all { it.isDigit() }) {
                    _uiState.update { it.copy(formTelefono = event.telefono) }
                }
            }
            is ClientesEvent.OnDeudaChange -> _uiState.update { it.copy(formDeuda = event.deuda) }

            ClientesEvent.GuardarCliente -> guardarCliente()
            is ClientesEvent.SaldarDeuda -> saldarDeudaCliente(event.idCliente)
        }
    }

    private fun saldarDeudaCliente(idCliente: Long) {
        val cliente = _uiState.value.todosLosClientes.find { it.idCliente == idCliente }
        if (cliente != null) {
            val clienteActualizado = cliente.copy(deuda = 0.0)
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
                try {
                    clienteRepository.updateCliente(idCliente, clienteActualizado)
                    _uiState.update { it.copy(mensaje = "¡Deuda saldada correctamente!") }
                    cargarDatos()
                } catch (e: Exception) {
                    _uiState.update { it.copy(isLoading = false, error = "No se pudo saldar la deuda: ${e.message}") }
                }
            }
        }
    }

    private fun guardarCliente() {
        val state = _uiState.value
        val clienteDatos = Cliente(
            idCliente = state.editandoClienteId ?: 0L,
            nombre = state.formNombre,
            telefono = state.formTelefono.toLongOrNull() ?: 0L,
            deuda = state.formDeuda.replace(",", ".").toDoubleOrNull() ?: 0.0
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, mensaje = null) }
            try {
                if (state.editandoClienteId != null) {
                    clienteRepository.updateCliente(state.editandoClienteId, clienteDatos)
                    _uiState.update { it.copy(mensaje = "Cliente actualizado con éxito") }
                } else {
                    clienteRepository.createCliente(clienteDatos)
                    _uiState.update { it.copy(mensaje = "Cliente guardado con éxito") }
                }
                cargarDatos()
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al guardar cliente: ${e.message}") }
            }
        }
    }
}