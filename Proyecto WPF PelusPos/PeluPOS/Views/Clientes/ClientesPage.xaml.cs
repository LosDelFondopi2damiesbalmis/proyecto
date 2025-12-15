using System.Threading.Tasks;
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

            // TODO: si más adelante usas un contenedor de dependencias (DI),
            // crea el servicio allí y resuélvelo en vez de instanciarlo a mano.
            IClienteService clienteService = new ClienteService();
            ViewModel = new ClientesViewModel(clienteService);

            DataContext = ViewModel;

            Loaded += ClientesPage_Loaded;
        }

        private async void ClientesPage_Loaded(object sender, System.Windows.RoutedEventArgs e)
        {
            await ViewModel.LoadAsync();
        }
    }
}

