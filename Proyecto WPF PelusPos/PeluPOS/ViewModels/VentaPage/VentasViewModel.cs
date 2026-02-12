using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Services.Ventas;

namespace PeluPOS.ViewModels.VentaPage
{
    public partial class VentasViewModel : ObservableObject
    {
        private readonly IVentaService _service;

        public ObservableCollection<VentaRowViewModel> Ventas { get; } = new();

        [ObservableProperty] private VentaRowViewModel? ventaSeleccionada;

        public VentasViewModel(IVentaService service)
        {
            _service = service;
        }

        public async Task LoadAsync()
        {
            Ventas.Clear();
            var facturas = await _service.GetAllAsync();
            foreach (var f in facturas)
                Ventas.Add(new VentaRowViewModel { Factura = f });
        }

        public async Task UpdatePagoAsync(long facturaId, string tipoPago, bool pendiente)
        {
            await _service.UpdatePagoAsync(facturaId, tipoPago, pendiente);
            await LoadAsync();
        }
    }
}
