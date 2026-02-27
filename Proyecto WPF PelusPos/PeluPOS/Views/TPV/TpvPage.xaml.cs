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
using System.Windows.Navigation;
using System.Windows.Shapes;
using PeluPOS.Services;
using PeluPOS.ViewModels.TPV;

namespace PeluPOS.Views.TPV
{
    /// <summary>
    /// Lógica de interacción para TpvPage.xaml
    /// </summary>
    public partial class TpvPage : Page
    {
        private readonly IAuthService _auth = new AuthService();

        public TpvViewModel ViewModel { get; }
        public string ActiveUserLabel =>
            SessionService.CurrentEmpleado != null
                ? $"{SessionService.CurrentEmpleado.Nombre}"
                : "Sin sesión";

        public TpvPage()
        {
            InitializeComponent();

            ViewModel = new TpvViewModel(new CatalogService(), new TpvVentaService());
            DataContext = ViewModel;

            SessionService.SessionChanged += () =>
            {
                // refrescar label superior
                Dispatcher.Invoke(() =>
                {
                    // hack simple para refrescar binding
                    DataContext = null;
                    DataContext = ViewModel;
                });
            };

            Loaded += async (_, __) =>
            {
                await EnsureLoginAsync();
                await ViewModel.LoadAsync();
            };
        }

        private async System.Threading.Tasks.Task EnsureLoginAsync()
        {
            if (SessionService.IsLoggedIn) return;

            var users = await _auth.GetUsersAsync();
            var vm = new SelectUserViewModel();
            foreach (var u in users) vm.Users.Add(u.Empleado);
            vm.SelectedUser = vm.Users.FirstOrDefault();

            var dlg = new SelectedUserDialog(vm)
            {
                Owner = Window.GetWindow(this)
            };

            if (dlg.ShowDialog() != true)
                return;

            var user = dlg.Selected!;
            var empleado = await _auth.ResolveEmpleadoAsync(user.Usuario);

            if (empleado == null)
            {
                MessageBox.Show("No se pudo asociar un empleado a este usuario.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            SessionService.Login(user.Usuario, empleado);
        }

        private void UserMenu_Click(object sender, RoutedEventArgs e)
        {
            var menu = new ContextMenu();

            var logout = new MenuItem { Header = "Cerrar sesión" };
            logout.Click += async (_, __) =>
            {
                SessionService.Logout();
                await EnsureLoginAsync();
            };

            menu.Items.Add(logout);

            menu.IsOpen = true;
        }

        private void Gear_Click(object sender, RoutedEventArgs e)
        {
            if (!SessionService.CanAccessDashboard)
            {
                MessageBox.Show("No tienes permisos para acceder al Dashboard.", "Acceso denegado",
                    MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }

            // Aquí navegas al DashboardPage (o a tu página principal)
            NavigationService?.Navigate(new DashboardPage()); // <- cambia el nombre si tu dashboard se llama distinto
        }

        private void Productos_Click(object sender, RoutedEventArgs e) => ViewModel.ModoProductos = true;
        private void Servicios_Click(object sender, RoutedEventArgs e) => ViewModel.ModoProductos = false;

        private async void Cobrar_Click(object sender, RoutedEventArgs e)
        {
            if (SessionService.CurrentEmpleado == null)
            {
                ViewModel.Error = "No hay empleado activo.";
                return;
            }

            await ViewModel.CobrarAsync(SessionService.CurrentEmpleado);
        }
    }
}
