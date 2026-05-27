using System.Windows;
using System.Windows.Controls;
using PeluPOS.Models.Entities;
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
                Owner = Window.GetWindow(this),
            };

            if (dlg.ShowDialog() != true) return;

            await ViewModel.AddLocalAsync(dialogVm.Nombre, dialogVm.Direccion);
        }

        private async void EditarLocal_Click(object sender, RoutedEventArgs e)
        {
            if ((sender as FrameworkElement)?.Tag is not Local local) return;

            var dialogVm = new NuevoLocalViewModel
            {
                Nombre = local.Nombre,
                Direccion = local.Direccion ?? string.Empty
            };

            var dlg = new NuevoLocalDialog(dialogVm, "Editar local")
            {
                Owner = Window.GetWindow(this),
            };

            if (dlg.ShowDialog() != true) return;

            await ViewModel.UpdateLocalAsync(local.Id, dialogVm.Nombre, dialogVm.Direccion);
        }

        private async void EliminarLocal_Click(object sender, RoutedEventArgs e)
        {
            if ((sender as FrameworkElement)?.Tag is not Local local) return;

            var result = MessageBox.Show(
                $"¿Estás seguro de que deseas eliminar el local \"{local.Nombre}\"?",
                "Confirmar eliminación",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning);

            if (result != MessageBoxResult.Yes) return;

            await ViewModel.DeleteLocalAsync(local.Id);
        }
    }
}
