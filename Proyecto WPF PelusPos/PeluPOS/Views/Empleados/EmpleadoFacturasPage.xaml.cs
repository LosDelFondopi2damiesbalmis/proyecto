using System.Windows.Controls;
using PeluPOS.Services;
using PeluPOS.ViewModels;

namespace PeluPOS.Views.Empleados
{
    /// <summary>
    /// Lógica de interacción para EmpleadoFacturasPage.xaml
    /// </summary>
    public partial class EmpleadoFacturasPage : Page
    {
        private readonly long _empleadoId;
        public EmpleadoFacturasViewModel ViewModel { get; }

        public EmpleadoFacturasPage(long empleadoId)
        {
            InitializeComponent();

            _empleadoId = empleadoId;
            ViewModel = new EmpleadoFacturasViewModel(AppServices.EmpleadoStats);
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync(_empleadoId);
        }

        private void Back_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            if (NavigationService?.CanGoBack == true) NavigationService.GoBack();
            else NavigationService?.Navigate(this);
        }
    }
}
