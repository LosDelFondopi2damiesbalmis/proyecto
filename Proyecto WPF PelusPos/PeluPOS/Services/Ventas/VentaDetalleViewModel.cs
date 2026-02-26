using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Ventas
{
    public partial class VentaDetalleViewModel : ObservableObject
    {
        public Factura Factura { get; }

        // Editables
        [ObservableProperty] private string tipoPago;
        [ObservableProperty] private bool pendiente;

        // Listas UI
        public ObservableCollection<string> TiposPago { get; } = new()
        {
            "Efectivo", "Tarjeta", "Bizum", "Transferencia", "Otro"
        };

        public VentaDetalleViewModel(Factura factura)
        {
            Factura = factura;

            tipoPago = factura.TipoPago;
            pendiente = factura.Pendiente;
        }
    }
}
