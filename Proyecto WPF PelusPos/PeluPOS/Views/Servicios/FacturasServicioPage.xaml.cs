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
using PeluPOS.Services.Servicios;
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
            ViewModel = new FacturasServicioViewModel(new ServicioService());
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
