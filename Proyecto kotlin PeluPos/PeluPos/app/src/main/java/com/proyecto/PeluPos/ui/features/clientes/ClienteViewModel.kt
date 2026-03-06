package com.proyecto.PeluPos.ui.features.clientes

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

@HiltViewModel
class ClientesViewModel @Inject constructor(
    private val clienteRepository: ClienteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientesUiState())
    val uiState: StateFlow<ClientesUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        val clientes = clienteRepository.getClientes()
        _uiState.update {
            it.copy(
                todosLosClientes = clientes,
                // Aplicamos el filtro por si recarga la lista mientras hay algo escrito
                clientesVisibles = filtrarClientes(clientes, it.searchQuery)
            )
        }
    }

    private fun filtrarClientes(lista: List<Cliente>, query: String): List<Cliente> {
        if (query.isBlank()) return lista
        return lista.filter { it.nombre.contains(query, ignoreCase = true) }
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

            ClientesEvent.PrepararNuevoCliente -> {
                _uiState.update {
                    it.copy(
                        editandoClienteId = null, formNombre = "", formTelefono = "", formDeuda = ""
                    )
                }
            }

            is ClientesEvent.PrepararEdicion -> {
                val cliente = _uiState.value.todosLosClientes.find { it.idCliente == event.idCliente }
                cliente?.let { cli ->
                    _uiState.update {
                        it.copy(
                            editandoClienteId = cli.idCliente,
                            formNombre = cli.nombre,
                            formTelefono = if (cli.telefono == 0L) "" else cli.telefono.toString(),
                            formDeuda = cli.deuda.toString()
                        )
                    }
                }
            }

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
        // 1. Buscamos al cliente en nuestra lista actual
        val cliente = _uiState.value.todosLosClientes.find { it.idCliente == idCliente }

        if (cliente != null) {
            // 2. Creamos una copia del cliente pero con la deuda a cero
            val clienteActualizado = cliente.copy(deuda = 0.0)

            // 3. Lo actualizamos en la base de datos (repositorio)
            clienteRepository.updateCliente(clienteActualizado)

            // 4. Recargamos la lista.
            // ¡Esto hará que el 'uiState' cambie y la tarjeta roja desaparezca de golpe!
            cargarDatos()
        }
    }

    private fun guardarCliente() {
        val state = _uiState.value
        val nuevoCliente = Cliente(
            idCliente = state.editandoClienteId ?: System.currentTimeMillis(),
            nombre = state.formNombre,
            telefono = state.formTelefono.toLongOrNull() ?: 0L,
            deuda = state.formDeuda.replace(",", ".").toDoubleOrNull() ?: 0.0
        )

        if (state.editandoClienteId != null) {
            clienteRepository.updateCliente(nuevoCliente)
        } else {
            clienteRepository.insert(nuevoCliente)
        }
        cargarDatos()
    }
}