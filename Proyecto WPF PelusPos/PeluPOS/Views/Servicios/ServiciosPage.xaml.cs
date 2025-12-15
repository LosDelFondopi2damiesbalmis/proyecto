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
using PeluPOS.Models.Entities;
using PeluPOS.Services.Servicios;
using PeluPOS.ViewModels.ServicioPage;

namespace PeluPOS.Views.Servicios
{
    /// <summary>
    /// Lógica de interacción para ServiciosPage.xaml
    /// </summary>
    public partial class ServiciosPage : Page
    {
        public ServiciosViewModel ViewModel { get; }

        public ServiciosPage()
        {
            InitializeComponent();
            ViewModel = new ServiciosViewModel(new ServicioService());
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync();
        }

        private async void CreateServicio_Click(object sender, RoutedEventArgs e)
        {
            var vm = new ServicioEditorViewModel();
            var dlg = new ServicioDialog("Crear servicio", vm) { Owner = Window.GetWindow(this) };
            if (dlg.ShowDialog() != true) return;

            await ViewModel.CreateAsync((vm.Nombre, vm.Precio, vm.Descripcion));
        }

        private async void EditServicio_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not Servicio servicio) return;

            var vm = new ServicioEditorViewModel
            {
                Nombre = servicio.Nombre,
                Precio = servicio.Precio,
                Descripcion = servicio.Descripcion
            };

            var dlg = new ServicioDialog("Editar servicio", vm) { Owner = Window.GetWindow(this) };
            if (dlg.ShowDialog() != true) return;

            await ViewModel.EditAsync((servicio.Id, vm.Nombre, vm.Precio, vm.Descripcion));
        }

        private async void DeleteServicio_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not long id) return;

            var confirm = MessageBox.Show("¿Seguro que quieres eliminar este servicio?",
                "Confirmar eliminación", MessageBoxButton.YesNo, MessageBoxImage.Warning);

            if (confirm != MessageBoxResult.Yes) return;

            await ViewModel.DeleteAsync(id);
        }

        private void OpenFacturas_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not long servicioId) return;

            NavigationService?.Navigate(new FacturasServicioPage(servicioId));
        }
    }
}
