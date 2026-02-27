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

        public MainWindow()
        {
            InitializeComponent();

            SessionService.SessionChanged += ApplySidebarVisibility;

            Loaded += async (_, __) =>
            {
                await EnsureLoginAsync();

                // Redirección según rol:
                if (SessionService.CurrentUser?.Roles == Roles.Empleado)
                {
                    MainFrame.Navigate(new TpvPage()); // TPV directo
                }
                else
                {
                    MainFrame.Navigate(new DashboardPage()); // Admin/Manager al dashboard
                }

                ApplySidebarVisibility();
            };
        }

        private async Task EnsureLoginAsync()
        {
            while (!SessionService.IsLoggedIn)
            {
                var users = await _auth.GetUsersAsync();
                var vm = new LoginViewModel();
                foreach (var u in users) vm.Users.Add(u.Empleado);
                vm.SelectedUser = vm.Users.Count > 0 ? vm.Users[0] : null;

                var dlg = new LoginDialog(vm, _auth) { Owner = this };

                if (dlg.ShowDialog() != true)
                {
                    Close(); // si cancelan, cierras la app o vuelve a pedir login
                    return;
                }

                var user = dlg.SelectedUser!;
                var emp = await _auth.ResolveEmpleadoAsync(user);

                if (emp == null)
                {
                    MessageBox.Show("Este usuario no tiene empleado asociado.", "Error",
                        MessageBoxButton.OK, MessageBoxImage.Error);
                    continue;
                }

                SessionService.Login(user, emp);
            }
        }

        private void ApplySidebarVisibility()
        {
            // Sidebar visible solo para Admin/Manager
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