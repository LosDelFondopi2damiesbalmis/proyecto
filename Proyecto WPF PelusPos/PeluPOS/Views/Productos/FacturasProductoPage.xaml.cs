using System.Windows.Controls;
using System.Windows.Navigation;
using PeluPOS.Services;
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
            ViewModel = new FacturasProductoViewModel(AppServices.Productos);
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
