using System.Windows.Controls;
using PeluPOS.Services;
using PeluPOS.ViewModels;

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

        private async void ClientesPage_Loaded(object sender, System.Windows.RoutedEventArgs e)
        {
            await ViewModel.LoadAsync();
        }
    }
}
