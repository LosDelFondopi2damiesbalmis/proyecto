using System.Windows;
using System.Windows.Controls;
using PeluPOS.Services;
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

            ViewModel = new LocalesViewModel(AppServices.Locales);

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
