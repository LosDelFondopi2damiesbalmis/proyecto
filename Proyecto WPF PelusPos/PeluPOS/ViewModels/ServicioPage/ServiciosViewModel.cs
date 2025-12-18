using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Servicios;

namespace PeluPOS.ViewModels.ServicioPage
{
    public partial class ServiciosViewModel : ObservableObject
    {
        private readonly IServicioService _service;

        private readonly List<Servicio> _todos = new();

        [ObservableProperty] private string? textoBusqueda;
        [ObservableProperty] private Servicio? servicioSeleccionado;
        [ObservableProperty] private bool isLoading;

        public ObservableCollection<Servicio> ServiciosFiltrados { get; } = new();
        public ObservableCollection<Factura> FacturasDelServicio { get; } = new();

        public ServiciosViewModel(IServicioService service)
        {
            _service = service;
        }

        public async Task LoadAsync()
        {
            if (IsLoading) return;
            IsLoading = true;

            try
            {
                _todos.Clear();
                var items = await _service.GetAllAsync();
                _todos.AddRange(items);

                AplicarFiltro();
            }
            finally
            {
                IsLoading = false;
            }
        }

        partial void OnTextoBusquedaChanged(string? value) => AplicarFiltro();

        partial void OnServicioSeleccionadoChanged(Servicio? value)
            => _ = LoadFacturasAsync(value);

        private void AplicarFiltro()
        {
            var filtro = TextoBusqueda?.Trim();
            IEnumerable<Servicio> res = _todos;

            if (!string.IsNullOrWhiteSpace(filtro))
            {
                res = res.Where(s => s.Nombre.Contains(filtro, StringComparison.OrdinalIgnoreCase));
            }

            ServiciosFiltrados.Clear();
            foreach (var s in res) ServiciosFiltrados.Add(s);
        }

        private async Task LoadFacturasAsync(Servicio? servicio)
        {
            FacturasDelServicio.Clear();
            if (servicio == null) return;

            var facturas = await _service.GetFacturasByServicioAsync(servicio.Id);
            foreach (var f in facturas) FacturasDelServicio.Add(f);
        }

        // -------- Commands --------

        [RelayCommand]
        public async Task CreateAsync((string Nombre, decimal Precio, string Descripcion) data)
        {
            var nuevo = await _service.AddAsync(data.Nombre, data.Precio, data.Descripcion);

            _todos.Add(nuevo);
            AplicarFiltro();

            ServicioSeleccionado = nuevo;
        }

        [RelayCommand]
        public async Task EditAsync((long Id, string Nombre, decimal Precio, string Descripcion) data)
        {
            await _service.UpdateAsync(data.Id, data.Nombre, data.Precio, data.Descripcion);

            // Refrescar lista base y UI (simple y fiable en mock)
            await LoadAsync();

            ServicioSeleccionado = _todos.FirstOrDefault(s => s.Id == data.Id);
        }

        [RelayCommand]
        public async Task DeleteAsync(long servicioId)
        {
            await _service.DeleteAsync(servicioId);

            await LoadAsync();

            if (ServicioSeleccionado?.Id == servicioId)
            {
                ServicioSeleccionado = null;
                FacturasDelServicio.Clear();
            }
        }
    }
}
