using System.Linq;
using System.Windows;
using System.Windows.Controls;
using PeluPOS.Models.Entities;
using PeluPOS.Services;
using PeluPOS.ViewModels;
using PeluPOS.Views.Empleados;

namespace PeluPOS.Views.EmpleadosPage
{
    /// <summary>
    /// Lógica de interacción para EmpleadosPage.xaml
    /// </summary>
    public partial class EmpleadosPage : Page
    {
        public EmpleadosViewModel ViewModel { get; }

        public EmpleadosPage()
        {
            InitializeComponent();

            ViewModel = new EmpleadosViewModel(AppServices.Empleados);
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync();
        }
        private void OpenStats_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not long empleadoId) return;
            NavigationService?.Navigate(new EmpleadoFacturasPage(empleadoId));
        }
        private async void CreateEmpleado_Click(object sender, RoutedEventArgs e)
        {
            var vm = new EmpleadoEditorViewModel();
            foreach (var l in ViewModel.Locales) vm.Locales.Add(l);
            vm.LocalSeleccionado = vm.Locales.FirstOrDefault();

            var dlg = new EmpleadoDialog("Crear empleado", vm, isCreate: true)
            {
                Owner = Window.GetWindow(this)
            };

            if (dlg.ShowDialog() != true) return;

            await ViewModel.CreateAsync((
                vm.Nombre,
                vm.Telefono,
                vm.Email,
                vm.Cargo,
                vm.LocalSeleccionado!.Id,
                vm.Password
            ));
        }

        private async void EditEmpleado_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not Empleado emp) return;

            var vm = new EmpleadoEditorViewModel
            {
                Nombre = emp.Nombre,
                Telefono = emp.Telefono,
                Email = emp.Email,
                Cargo = emp.Cargo
            };

            foreach (var l in ViewModel.Locales) vm.Locales.Add(l);
            vm.LocalSeleccionado =
                vm.Locales.FirstOrDefault(x => x.Id == emp.Local?.Id) ?? vm.Locales.FirstOrDefault();

            var dlg = new EmpleadoDialog("Editar empleado", vm, isCreate: false)
            {
                Owner = Window.GetWindow(this)
            };

            if (dlg.ShowDialog() != true) return;

            await ViewModel.EditAsync((
                emp.Id,
                vm.Nombre,
                vm.Telefono,
                vm.Email,
                vm.Cargo,
                vm.LocalSeleccionado!.Id
            ));
        }

        private async void DeleteEmpleado_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not long id) return;

            var confirm = MessageBox.Show(
                "¿Seguro que quieres eliminar este empleado?",
                "Confirmar eliminación",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning);

            if (confirm != MessageBoxResult.Yes) return;

            await ViewModel.DeleteAsync(id);
        }
    }
}
