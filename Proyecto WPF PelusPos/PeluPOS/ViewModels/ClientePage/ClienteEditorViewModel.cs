using CommunityToolkit.Mvvm.ComponentModel;

namespace PeluPOS.ViewModels
{
    public partial class ClienteEditorViewModel : ObservableObject
    {
        [ObservableProperty] private string nombre = string.Empty;
        [ObservableProperty] private long telefono;
        [ObservableProperty] private decimal deuda;
        [ObservableProperty] private string? error;

        public bool IsValid =>
            !string.IsNullOrWhiteSpace(Nombre);

        public void Validate()
        {
            if (string.IsNullOrWhiteSpace(Nombre))
                Error = "El nombre es obligatorio.";
            else
                Error = null;
        }
    }
}
