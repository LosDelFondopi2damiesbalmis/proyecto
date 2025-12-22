using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Productos;

namespace PeluPOS.ViewModels.ProductoPage
{
    public partial class FacturasProductoViewModel : ObservableObject
    {
        private readonly IProductoService _service;

        [ObservableProperty] private Producto? producto;

        public ObservableCollection<Factura> Facturas { get; } = new();

        public FacturasProductoViewModel(IProductoService service)
        {
            _service = service;
        }

        public async Task LoadAsync(long productoId)
        {
            var all = await _service.GetAllAsync();
            Producto = all.FirstOrDefault(p => p.Id == productoId);

            Facturas.Clear();
            var facturas = await _service.GetFacturasByProductoAsync(productoId);
            foreach (var f in facturas) Facturas.Add(f);
        }
    }
}
