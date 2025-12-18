using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;

namespace PeluPOS.ViewModels.ProductoPage
{
    public partial class ProductoEditorViewModel : ObservableObject
    {
        [ObservableProperty] private string nombre = string.Empty;
        [ObservableProperty] private decimal precioCompra;
        [ObservableProperty] private decimal precioVenta;
        [ObservableProperty] private long stock;

        [ObservableProperty] private string? error;

        public bool IsValid =>
            !string.IsNullOrWhiteSpace(Nombre) &&
            PrecioCompra >= 0 &&
            PrecioVenta >= 0 &&
            Stock >= 0;

        public void Validate()
        {
            if (string.IsNullOrWhiteSpace(Nombre))
                Error = "El nombre del producto es obligatorio.";
            else if (PrecioCompra < 0 || PrecioVenta < 0)
                Error = "Los precios no pueden ser negativos.";
            else if (Stock < 0)
                Error = "El stock no puede ser negativo.";
            else
                Error = null;
        }
    }
}
