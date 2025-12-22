using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Servicios;

namespace PeluPOS.ViewModels.ServicioPage
{
    public partial class FacturasServicioViewModel : ObservableObject
    {
        private readonly IServicioService _service;

        [ObservableProperty] private Servicio? servicio;
        [ObservableProperty] private bool isLoading;

        public ObservableCollection<Factura> Facturas { get; } = new();

        public FacturasServicioViewModel(IServicioService service)
        {
            _service = service;
        }

        public async Task LoadAsync(long servicioId)
        {
            if (IsLoading) return;
            IsLoading = true;

            try
            {
                // Cargar el servicio (para mostrar el nombre en el header)
                var all = await _service.GetAllAsync();
                Servicio = all.FirstOrDefault(s => s.Id == servicioId);

                // Cargar facturas relacionadas
                Facturas.Clear();
                var facturas = await _service.GetFacturasByServicioAsync(servicioId);

                foreach (var f in facturas)
                    Facturas.Add(f);
            }
            finally
            {
                IsLoading = false;
            }
        }
    }
}
