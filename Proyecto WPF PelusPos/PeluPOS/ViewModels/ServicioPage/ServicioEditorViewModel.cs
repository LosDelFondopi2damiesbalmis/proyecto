using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PeluPOS.ViewModels.ServicioPage
{
    public partial class ServicioEditorViewModel : ObservableObject
    {
        [ObservableProperty] private string nombre = string.Empty;
        [ObservableProperty] private decimal precio;
        [ObservableProperty] private string descripcion = string.Empty;

        [ObservableProperty] private string? error;

        public bool IsValid => !string.IsNullOrWhiteSpace(Nombre) && Precio >= 0;

        public void Validate()
        {
            if (string.IsNullOrWhiteSpace(Nombre))
                Error = "El nombre del servicio es obligatorio.";
            if (Precio < 0)
                Error = "El precio no puede ser negativo.";
            else
                Error = null;
        }
    }
}
