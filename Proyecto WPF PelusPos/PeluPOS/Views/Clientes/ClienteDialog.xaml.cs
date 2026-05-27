using System.Windows;
using PeluPOS.ViewModels;

namespace PeluPOS.Views.Clientes
{
    public partial class ClienteDialog : Window
    {
        public ClienteEditorViewModel VM { get; }
        public string DialogTitle { get; }

        public ClienteDialog(string title, ClienteEditorViewModel vm)
        {
            InitializeComponent();

            DialogTitle = title;
            VM = vm;

            DataContext = this;
        }

        private void Cancelar_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }

        private void Guardar_Click(object sender, RoutedEventArgs e)
        {
            VM.Validate();

            if (!VM.IsValid)
                return;

            DialogResult = true;
            Close();
        }
    }
}
