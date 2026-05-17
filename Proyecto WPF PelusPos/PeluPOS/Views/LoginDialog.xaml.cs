using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using PeluPOS.Models.Entities;
using PeluPOS.Services;
using PeluPOS.Models.ApiDtos.Auth;
using PeluPOS.Services.Api;
using PeluPOS.ViewModels.Login;

namespace PeluPOS.Views
{
    /// <summary>
    /// Lógica de interacción para LoginDialog.xaml
    /// </summary>
    public partial class LoginDialog : Window
    {
        private readonly AuthApiService _authApiService;
        public LoginViewModel ViewModel { get; }

        public LoginDialog(LoginViewModel vm, AuthApiService authApiService)
        {
            InitializeComponent();
            ViewModel = vm;
            _authApiService = authApiService;
            DataContext = ViewModel;
        }

        public LoginResponseDto? LoginResult { get; private set; }

        private void Salir_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }

        private async void Entrar_Click(object sender, RoutedEventArgs e)
        {
            ViewModel.Error = string.Empty;

            if (ViewModel.UsuarioSeleccionado == null)
            {
                ViewModel.Error = "Selecciona un usuario.";
                return;
            }

            var password = PasswordBox.Password?.Trim();
            if (string.IsNullOrWhiteSpace(password))
            {
                ViewModel.Error = "Introduce la contraseña.";
                return;
            }

            var request = new LoginRequestDto
            {
                usuario = ViewModel.UsuarioSeleccionado.usuario,
                contrasena = password
            };

            var response = await _authApiService.LoginAsync(request);

            if (response == null)
            {
                ViewModel.Error = "No se pudo conectar con la API.";
                return;
            }

            if (string.IsNullOrWhiteSpace(response.jwtToken))
            {
                ViewModel.Error = response.mensaje ?? "Credenciales incorrectas.";
                return;
            }

            LoginResult = response;
            DialogResult = true;
            Close();
        }
    }
}
