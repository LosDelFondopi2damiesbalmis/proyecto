using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;

namespace PeluPOS.ViewModels.TPV
{
    public partial class TpvLineaViewModel : ObservableObject
    {
        public LineaFactura Linea { get; }

        public TpvLineaViewModel(LineaFactura linea)
        {
            Linea = linea;
        }

        public string Nombre => Linea.Producto?.Nombre ?? Linea.Servicio?.Nombre ?? "";
        public decimal PrecioUnitario => Linea.PrecioUnitario;

        public int Cantidad
        {
            get => Linea.Cantidad;
            set
            {
                if (value < 1) value = 1;
                Linea.Cantidad = value;
                OnPropertyChanged();
                OnPropertyChanged(nameof(Subtotal));
            }
        }

        public decimal Subtotal => Linea.Cantidad * Linea.PrecioUnitario;
    }
}
