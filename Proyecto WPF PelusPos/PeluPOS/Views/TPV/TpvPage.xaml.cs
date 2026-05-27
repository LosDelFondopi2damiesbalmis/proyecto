using System.Windows;
using System.Windows.Controls;
using PeluPOS.Services;
using PeluPOS.ViewModels.TPV;

namespace PeluPOS.Views.TPV
{
    /// <summary>
    /// Lógica de interacción para TpvPage.xaml
    /// </summary>
    public partial class TpvPage : Page
    {
        public TpvViewModel ViewModel { get; }
        public string ActiveUserLabel =>
            SessionService.CurrentUsername != null
                ? SessionService.CurrentUsername
                : "Sin sesión";

        public TpvPage()
        {
            InitializeComponent();

            ViewModel = new TpvViewModel(AppServices.Catalog, AppServices.TpvVenta);
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
                await ViewModel.LoadAsync();
            };
        }

        private void UserMenu_Click(object sender, RoutedEventArgs e)
        {
            var menu = new ContextMenu();

            var logout = new MenuItem { Header = "Cerrar sesión" };
            logout.Click += (_, __) => SessionService.Logout();

            menu.Items.Add(logout);

            menu.IsOpen = true;
        }

        private void Gear_Click(object sender, RoutedEventArgs e)
        {
            if (!SessionService.CanSeeSidebar)
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
            await ViewModel.CobrarAsync(SessionService.CurrentEmpleadoId);
        }
    }
}
