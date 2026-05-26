using System.Windows;
using PeluPOS.Models.ApiDtos.Auth;
using PeluPOS.Services.Api;
using PeluPOS.ViewModels.Login;

namespace PeluPOS.Views
{
    public partial class LoginDialog : Window
    {
        private readonly AuthApiService _authApiService;
        private readonly UsuarioApiService _usuarioApiService;
        public LoginViewModel ViewModel { get; }

        public LoginDialog(LoginViewModel vm, AuthApiService authApiService, UsuarioApiService usuarioApiService)
        {
            InitializeComponent();
            ViewModel = vm;
            _authApiService = authApiService;
            _usuarioApiService = usuarioApiService;
            DataContext = ViewModel;

            Loaded += async (_, __) => await CargarUsuariosAsync();
        }

        private async Task CargarUsuariosAsync()
        {
            try
            {
                var usuarios = await _usuarioApiService.GetUsuariosAsync();
                ViewModel.Usuarios.Clear();
                foreach (var u in usuarios)
                    ViewModel.Usuarios.Add(u);
                ViewModel.UsuarioSeleccionado = ViewModel.Usuarios.FirstOrDefault();
            }
            catch
            {
                ViewModel.Error = "No se pudo cargar la lista de usuarios. Comprueba la conexión con la API.";
            }
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
