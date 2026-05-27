using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PeluPOS.ViewModels
{
    public partial class NuevoLocalViewModel : ObservableObject
    {
        [ObservableProperty] private string titulo = "Crear nuevo local";
        [ObservableProperty] private string nombre = string.Empty;
        [ObservableProperty] private string direccion = string.Empty;

        // Para mostrar mensajes simples en el diálogo (opcional)
        [ObservableProperty] private string? error;

        public bool IsValid => !string.IsNullOrWhiteSpace(Nombre);

        public void Validate()
        {
            Error = string.IsNullOrWhiteSpace(Nombre)
                ? "El nombre del local es obligatorio."
                : null;
        }
    }
}
