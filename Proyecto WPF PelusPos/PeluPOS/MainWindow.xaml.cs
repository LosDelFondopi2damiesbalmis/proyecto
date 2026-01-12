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
using PeluPOS.Views;
using PeluPOS.Views.EmpleadosPage;
using PeluPOS.Views.Locales;
using PeluPOS.Views.Productos;
using PeluPOS.Views.Servicios;

namespace PeluPOS
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

            // Landing: Dashboard al abrir la app
            MainFrame.Navigate(new DashboardPage());
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
            // MainFrame.Navigate(new VentasPage());
        }

        private void Usuarios_Click(object sender, RoutedEventArgs e)
        {
            // MainFrame.Navigate(new UsuariosPage());
        }

        private void Locales_Click(object sender, RoutedEventArgs e)
        {
            MainFrame.Navigate(new LocalesPage());
        }

        private void Tpv_Click(object sender, RoutedEventArgs e)
        {
            // MainFrame.Navigate(new TpvPage());
        }
    }
}