using System.Windows.Controls;
using PeluPOS.Services;
using PeluPOS.ViewModels.ServicioPage;

namespace PeluPOS.Views.Servicios
{
    /// <summary>
    /// Lógica de interacción para FacturasServicioPage.xaml
    /// </summary>
    public partial class FacturasServicioPage : Page
    {
        private readonly long _servicioId;
        public FacturasServicioViewModel ViewModel { get; }

        public FacturasServicioPage(long servicioId)
        {
            InitializeComponent();

            _servicioId = servicioId;
            ViewModel = new FacturasServicioViewModel(AppServices.Servicios);
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync(_servicioId);
        }

        private void Back_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            if (NavigationService?.CanGoBack == true) NavigationService.GoBack();
            else NavigationService?.Navigate(new ServiciosPage());
        }
    }
}
