using System.Collections.ObjectModel;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.ApiDtos.Usuarios;

namespace PeluPOS.ViewModels.Login
{
    public partial class LoginViewModel : ObservableObject
    {
        public ObservableCollection<UsuarioDto> Usuarios { get; } = new();

        [ObservableProperty]
        private UsuarioDto? usuarioSeleccionado;

        [ObservableProperty]
        private string error = string.Empty;

        public bool PuedeEntrar => UsuarioSeleccionado != null;

        partial void OnUsuarioSeleccionadoChanged(UsuarioDto? value)
        {
            OnPropertyChanged(nameof(PuedeEntrar));
        }
    }
}
