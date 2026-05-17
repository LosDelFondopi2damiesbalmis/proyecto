using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using PeluPOS.Models.Enums;
using PeluPOS.Services;
using PeluPOS.Views;
using PeluPOS.Views.EmpleadosPage;
using PeluPOS.Views.Locales;
using PeluPOS.Views.Productos;
using PeluPOS.Views.Servicios;
using PeluPOS.Views.TPV;
using PeluPOS.Views.Usuarios;
using PeluPOS.Views.Ventas;

namespace PeluPOS
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly IAuthService _auth = new AuthService();
        private readonly ApiClient _apiClient = new ApiClient("AQUI_TU_BASE_URL");
        private readonly AuthApiService _authApiService;
        private readonly UsuarioApiService _usuarioApiService;

        public MainWindow()
        {
            InitializeComponent();

            _authApiService = new AuthApiService(_apiClient);
            _usuarioApiService = new UsuarioApiService(_apiClient);

            SessionService.SessionChanged += ApplySidebarVisibility;
            SessionService.LoginRequired += async () =>
            {
                await Dispatcher.InvokeAsync(async () =>
                {
                    var ok = await ShowLoginAsync();
                    if (!ok)
                    {
                        Close();
                        return;
                    }

                    ApplySidebarVisibility();
                    MainFrame.Navigate(new TpvPage());
                });
            };

            Loaded += async (_, __) =>
            {
                var ok = await ShowLoginAsync();
                if (!ok)
                {
                    Close();
                    return;
                }

                ApplySidebarVisibility();
                MainFrame.Navigate(new TpvPage());
            };
        }
        private async Task<bool> ShowLoginAsync()
        {
            while (!SessionService.IsLoggedIn)
            {
                var usuarios = await _usuarioApiService.GetUsuariosAsync();

                var vm = new PeluPOS.ViewModels.Login.LoginViewModel();
                foreach (var u in usuarios)
                    vm.Usuarios.Add(u);

                vm.UsuarioSeleccionado = vm.Usuarios.FirstOrDefault();

                var dlg = new PeluPOS.Views.Login.LoginDialog(vm, _authApiService)
                {
                    Owner = this
                };

                if (dlg.ShowDialog() != true || dlg.LoginResult == null)
                    return false;

                var result = dlg.LoginResult;

                _apiClient.SetBearerToken(result.jwtToken);

                var role = RoleMapper.Parse(result.rolUsuario!);

                long userId = long.Parse(result.idUsuario!);
                long? empleadoId = null;

                if (!string.IsNullOrWhiteSpace(result.idEmpleado))
                    empleadoId = long.Parse(result.idEmpleado);

                SessionService.Login(
                    result.jwtToken!,
                    userId,
                    empleadoId,
                    result.usuario ?? vm.UsuarioSeleccionado!.usuario,
                    role
                );
            }

            return true;
        }

        private void ApplySidebarVisibility()
        {
            Sidebar.Visibility = SessionService.CanSeeSidebar
                ? Visibility.Visible
                : Visibility.Collapsed;
        }

        private void Dashboard_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new DashboardPage());
        }

        private void Clientes_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new ClientesPage());
        }

        private void Servicios_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new ServiciosPage());
        }

        private void Productos_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new ProductosPage());
        }

        private void Empleados_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new EmpleadosPage());
        }

        private void Ventas_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new VentasPage());
        }

        private void Usuarios_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new UsuariosPage());
        }

        private void Locales_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new LocalesPage());
        }

        private void Tpv_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new TpvPage());
        }
    }
}