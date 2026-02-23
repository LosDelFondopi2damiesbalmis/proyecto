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
using PeluPOS.Services.Ventas;
using PeluPOS.ViewModels.VentaPage;

namespace PeluPOS.Views.Ventas
{
    /// <summary>
    /// Lógica de interacción para VentasPage.xaml
    /// </summary>
    public partial class VentasPage : Page
    {
        public VentasViewModel ViewModel { get; }

        public VentasPage()
        {
            InitializeComponent();

            ViewModel = new VentasViewModel(new MockVentaService());
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync();
        }

        private void VentasGrid_DoubleClick(object sender, System.Windows.Input.MouseButtonEventArgs e)
        {
            if (ViewModel.VentaSeleccionada == null) return;
            OpenDetalle(ViewModel.VentaSeleccionada);
        }

        private void Ver_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not VentaRowViewModel row) return;
            OpenDetalle(row);
        }

        private async void OpenDetalle(VentaRowViewModel row)
        {
            var vm = new VentaDetalleViewModel(row.Factura);

            var dlg = new VentaDetalleDialog(vm)
            {
                Owner = Window.GetWindow(this)
            };

            if (dlg.ShowDialog() == true)
            {
                // Solo actualizamos TipoPago + Pendiente
                await ViewModel.UpdatePagoAsync(row.Id, vm.TipoPago, vm.Pendiente);
            }
        }
    }
}
