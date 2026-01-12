using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;
using PeluPOS.Services;

namespace PeluPOS.ViewModels
{
    public partial class EmpleadoFacturasViewModel : ObservableObject
    {
        private readonly IEmpleadoStatsService _service;

        [ObservableProperty] private string empleadoNombre = "Empleado";
        public ObservableCollection<Factura> Facturas { get; } = new();

        public EmpleadoFacturasViewModel(IEmpleadoStatsService service)
        {
            _service = service;
        }

        public async Task LoadAsync(long empleadoId)
        {
            EmpleadoNombre = await _service.GetEmpleadoNombreAsync(empleadoId);

            Facturas.Clear();
            var facturas = await _service.GetFacturasByEmpleadoAsync(empleadoId);
            foreach (var f in facturas) Facturas.Add(f);
        }
    }
}
