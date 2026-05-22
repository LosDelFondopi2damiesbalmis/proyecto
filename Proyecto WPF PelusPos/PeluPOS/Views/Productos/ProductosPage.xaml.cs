using System.Windows;
using System.Windows.Controls;
using PeluPOS.Models.Entities;
using PeluPOS.Services;
using PeluPOS.ViewModels.ProductoPage;

namespace PeluPOS.Views.Productos
{
    /// <summary>
    /// Lógica de interacción para ProductosPage.xaml
    /// </summary>
    public partial class ProductosPage : Page
    {
        public ProductosViewModel ViewModel { get; }

        public ProductosPage()
        {
            InitializeComponent();

            ViewModel = new ProductosViewModel(AppServices.Productos);
            DataContext = ViewModel;

            Loaded += async (_, __) => await ViewModel.LoadAsync();
        }

        private async void CreateProducto_Click(object sender, RoutedEventArgs e)
        {
            var vm = new ProductoEditorViewModel();
            var dlg = new ProductoDialog("Crear producto", vm) { Owner = Window.GetWindow(this) };
            if (dlg.ShowDialog() != true) return;

            await ViewModel.CreateAsync((vm.Nombre, vm.PrecioCompra, vm.PrecioVenta, vm.Stock));
        }

        private async void EditProducto_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not Producto p) return;

            var vm = new ProductoEditorViewModel
            {
                Nombre = p.Nombre,
                PrecioCompra = p.PrecioCompra,
                PrecioVenta = p.PrecioVenta,
                Stock = p.Stock
            };

            var dlg = new ProductoDialog("Editar producto", vm) { Owner = Window.GetWindow(this) };
            if (dlg.ShowDialog() != true) return;

            await ViewModel.EditAsync((p.Id, vm.Nombre, vm.PrecioCompra, vm.PrecioVenta));
        }

        private async void EditStock_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not Producto p) return;

            var vm = new StockEditorViewModel { Stock = p.Stock };
            var dlg = new StockDialog($"Modificar stock — {p.Nombre}", vm) { Owner = Window.GetWindow(this) };
            if (dlg.ShowDialog() != true) return;

            await ViewModel.UpdateStockAsync((p.Id, vm.Stock));
        }

        private async void DeleteProducto_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not long id) return;

            var confirm = MessageBox.Show(
                "¿Seguro que quieres eliminar este producto?",
                "Confirmar eliminación",
                MessageBoxButton.YesNo,
                MessageBoxImage.Warning);

            if (confirm != MessageBoxResult.Yes) return;

            await ViewModel.DeleteAsync(id);
        }

        private void OpenVentas_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not long productoId) return;
            NavigationService?.Navigate(new FacturasProductoPage(productoId));
        }
    }
}
