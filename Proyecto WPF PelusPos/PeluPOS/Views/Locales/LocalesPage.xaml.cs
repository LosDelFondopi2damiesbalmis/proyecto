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
using PeluPOS.Services.Locales;
using PeluPOS.ViewModels;
using PeluPOS.ViewModels.LocalPage;

namespace PeluPOS.Views.Locales
{
    /// <summary>
    /// Lógica de interacción para LocalesPage.xaml
    /// </summary>
    public partial class LocalesPage : Page
    {
        public LocalesViewModel ViewModel { get; }

        public LocalesPage()
        {
            InitializeComponent();

            // TODO: si usas DI, resuélvelo desde el contenedor.
            var localService = new LocalService();
            ViewModel = new LocalesViewModel(localService);

            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync();
        }

        private async void AddLocal_Click(object sender, RoutedEventArgs e)
        {
            var dialogVm = new NuevoLocalViewModel();
            var dlg = new NuevoLocalDialog(dialogVm)
            {
                Owner = Window.GetWindow(this), // importante al ser Page
            };

            var ok = dlg.ShowDialog() == true;
            if (!ok) return;

            await ViewModel.AddLocalAsync(dialogVm.Nombre, dialogVm.Direccion);
        }
    }
}
