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
using PeluPOS.Services.Productos;
using PeluPOS.ViewModels.ProductoPage;

namespace PeluPOS.Views.Productos
{
    /// <summary>
    /// Lógica de interacción para FacturasProductoPage.xaml
    /// </summary>
    public partial class FacturasProductoPage : Page
    {
        private readonly long _productoId;
        public FacturasProductoViewModel ViewModel { get; }

        public FacturasProductoPage(long productoId)
        {
            InitializeComponent();

            _productoId = productoId;
            ViewModel = new FacturasProductoViewModel(new ProductoService());
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync(_productoId);
        }

        private void Back_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            if (NavigationService?.CanGoBack == true) NavigationService.GoBack();
            else NavigationService?.Navigate(new ProductosPage());
        }
    }
}
