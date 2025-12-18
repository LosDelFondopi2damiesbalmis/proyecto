using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Productos;

namespace PeluPOS.ViewModels.ProductoPage
{
    public partial class ProductosViewModel : ObservableObject
    {
        private readonly IProductoService _service;

        private readonly List<Producto> _todos = new();

        [ObservableProperty] private string? textoBusqueda;
        [ObservableProperty] private Producto? productoSeleccionado;

        public ObservableCollection<Producto> ProductosFiltrados { get; } = new();

        public ProductosViewModel(IProductoService service)
        {
            _service = service;
        }

        public async Task LoadAsync()
        {
            _todos.Clear();
            var items = await _service.GetAllAsync();
            _todos.AddRange(items);

            AplicarFiltro();
        }

        partial void OnTextoBusquedaChanged(string? value) => AplicarFiltro();

        private void AplicarFiltro()
        {
            var filtro = TextoBusqueda?.Trim();
            IEnumerable<Producto> res = _todos;

            if (!string.IsNullOrWhiteSpace(filtro))
                res = res.Where(p => p.Nombre.Contains(filtro, StringComparison.OrdinalIgnoreCase));

            ProductosFiltrados.Clear();
            foreach (var p in res) ProductosFiltrados.Add(p);
        }

        [RelayCommand]
        public async Task CreateAsync((string Nombre, decimal Compra, decimal Venta, long Stock) data)
        {
            var nuevo = await _service.AddAsync(data.Nombre, data.Compra, data.Venta, data.Stock);
            _todos.Add(nuevo);
            AplicarFiltro();
            ProductoSeleccionado = nuevo;
        }

        [RelayCommand]
        public async Task EditAsync((long Id, string Nombre, decimal Compra, decimal Venta) data)
        {
            await _service.UpdateAsync(data.Id, data.Nombre, data.Compra, data.Venta);
            await LoadAsync();
            ProductoSeleccionado = _todos.FirstOrDefault(p => p.Id == data.Id);
        }

        [RelayCommand]
        public async Task UpdateStockAsync((long Id, long NewStock) data)
        {
            await _service.UpdateStockAsync(data.Id, data.NewStock);
            await LoadAsync();
            ProductoSeleccionado = _todos.FirstOrDefault(p => p.Id == data.Id);
        }

        [RelayCommand]
        public async Task DeleteAsync(long id)
        {
            await _service.DeleteAsync(id);
            await LoadAsync();
            if (ProductoSeleccionado?.Id == id) ProductoSeleccionado = null;
        }
    }
}
