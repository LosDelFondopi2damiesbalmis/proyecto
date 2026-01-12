using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PeluPOS.ViewModels
{
    public partial class EmpleadoEditorViewModel : ObservableObject
    {
        [ObservableProperty] private string nombre = string.Empty;
        [ObservableProperty] private long telefono;
        [ObservableProperty] private string email = string.Empty;
        [ObservableProperty] private string cargo = string.Empty;

        [ObservableProperty] private Local? localSeleccionado;

        // Solo CREAR
        [ObservableProperty] private string password = string.Empty;

        [ObservableProperty] private string? error;

        public ObservableCollection<Local> Locales { get; } = new();

        public bool IsValidCreate =>
            !string.IsNullOrWhiteSpace(Nombre) &&
            !string.IsNullOrWhiteSpace(Email) &&
            LocalSeleccionado != null &&
            !string.IsNullOrWhiteSpace(Password);

        public bool IsValidEdit =>
            !string.IsNullOrWhiteSpace(Nombre) &&
            !string.IsNullOrWhiteSpace(Email) &&
            LocalSeleccionado != null;

        public void ValidateCreate()
        {
            if (string.IsNullOrWhiteSpace(Nombre)) Error = "El nombre es obligatorio.";
            else if (string.IsNullOrWhiteSpace(Email)) Error = "El email es obligatorio.";
            else if (LocalSeleccionado == null) Error = "Selecciona un local.";
            else if (string.IsNullOrWhiteSpace(Password)) Error = "La contraseña es obligatoria.";
            else Error = null;
        }

        public void ValidateEdit()
        {
            if (string.IsNullOrWhiteSpace(Nombre)) Error = "El nombre es obligatorio.";
            else if (string.IsNullOrWhiteSpace(Email)) Error = "El email es obligatorio.";
            else if (LocalSeleccionado == null) Error = "Selecciona un local.";
            else Error = null;
        }
    }
}
