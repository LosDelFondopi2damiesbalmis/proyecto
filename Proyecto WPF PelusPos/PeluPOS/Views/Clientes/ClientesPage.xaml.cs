using System.Windows;
using System.Windows.Controls;
using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.Entities;
using PeluPOS.Services;
using PeluPOS.ViewModels;
using PeluPOS.Views.Clientes;

namespace PeluPOS.Views
{
    public partial class ClientesPage : Page
    {
        public ClientesViewModel ViewModel { get; }

        public ClientesPage()
        {
            InitializeComponent();

            ViewModel = new ClientesViewModel(AppServices.Clientes);

            DataContext = ViewModel;

            Loaded += ClientesPage_Loaded;
        }

        private async void ClientesPage_Loaded(object sender, RoutedEventArgs e)
        {
            await ViewModel.LoadAsync();
        }

        // ── Nuevo cliente ────────────────────────────────────────
        private async void NuevoCliente_Click(object sender, RoutedEventArgs e)
        {
            var editorVm = new ClienteEditorViewModel();
            var dialog = new ClienteDialog("Nuevo Cliente", editorVm)
            {
                Owner = Window.GetWindow(this)
            };

            if (dialog.ShowDialog() != true) return;

            var dto = new ClienteDto
            {
                nombre   = editorVm.Nombre,
                telefono = editorVm.Telefono,
                deuda    = editorVm.Deuda
            };

            var ok = await ViewModel.CreateClienteAsync(dto);

            if (!ok)
                MessageBox.Show("No se pudo crear el cliente.", "Error",
                                MessageBoxButton.OK, MessageBoxImage.Error);
        }

        // ── Editar cliente ───────────────────────────────────────
        private async void EditarCliente_Click(object sender, RoutedEventArgs e)
        {
            if (((Button)sender).Tag is not Cliente cliente) return;

            var editorVm = new ClienteEditorViewModel
            {
                Nombre   = cliente.Nombre,
                Telefono = cliente.Telefono,
                Deuda    = cliente.Deuda
            };

            var dialog = new ClienteDialog("Editar Cliente", editorVm)
            {
                Owner = Window.GetWindow(this)
            };

            if (dialog.ShowDialog() != true) return;

            var dto = new ClienteDto
            {
                idCliente = cliente.Id,
                nombre    = editorVm.Nombre,
                telefono  = editorVm.Telefono,
                deuda     = editorVm.Deuda
            };

            var ok = await ViewModel.UpdateClienteAsync(dto);

            if (!ok)
                MessageBox.Show("No se pudo actualizar el cliente.", "Error",
                                MessageBoxButton.OK, MessageBoxImage.Error);
        }

        // ── Eliminar cliente ─────────────────────────────────────
        private async void EliminarCliente_Click(object sender, RoutedEventArgs e)
        {
            if (((Button)sender).Tag is not Cliente cliente) return;

            var result = MessageBox.Show(
                $"¿Seguro que quieres eliminar al cliente «{cliente.Nombre}»?",
                "Confirmar eliminación",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning);

            if (result != MessageBoxResult.Yes) return;

            var ok = await ViewModel.DeleteClienteAsync(cliente.Id);

            if (!ok)
                MessageBox.Show("No se pudo eliminar el cliente.", "Error",
                                MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}
