using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;
using PeluPOS.Services;


namespace PeluPOS.ViewModels
{
    public partial class ClientesViewModel : ObservableObject
    {
        private readonly IClienteService _clienteService;

        // Lista base (sin filtrar)
        private readonly List<Cliente> _todosLosClientes = new();

        // Texto del buscador
        [ObservableProperty]
        private string? textoBusqueda;

        // Cliente seleccionado en la lista
        [ObservableProperty]
        private Cliente? clienteSeleccionado;

        // Para mostrar un spinner o similar si quieres
        [ObservableProperty]
        private bool isLoading;

        // Lista que ve la UI (filtrada)
        public ObservableCollection<Cliente> ClientesFiltrados { get; } =
            new ObservableCollection<Cliente>();

        public ClientesViewModel(IClienteService clienteService)
        {
            _clienteService = clienteService;
        }

        // Carga inicial de clientes
        public async Task LoadAsync()
        {
            if (IsLoading) return;
            IsLoading = true;

            try
            {
                _todosLosClientes.Clear();
                var clientes = await _clienteService.GetAllAsync();
                _todosLosClientes.AddRange(clientes);

                AplicarFiltro();
            }
            finally
            {
                IsLoading = false;
            }
        }

        // Se llama automáticamente cuando cambia TextoBusqueda (gracias a CommunityToolkit)
        partial void OnTextoBusquedaChanged(string? value)
        {
            AplicarFiltro();
        }

        private void AplicarFiltro()
        {
            var filtro = TextoBusqueda?.Trim();

            IEnumerable<Cliente> resultado = _todosLosClientes;

            if (!string.IsNullOrWhiteSpace(filtro))
            {
                resultado = resultado.Where(c =>
                    c.Nombre.Contains(filtro, StringComparison.OrdinalIgnoreCase));
            }

            ClientesFiltrados.Clear();
            foreach (var cli in resultado)
                ClientesFiltrados.Add(cli);
        }
    }
}

