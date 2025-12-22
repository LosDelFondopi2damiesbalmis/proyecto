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
using System.Windows.Shapes;
using PeluPOS.ViewModels.ServicioPage;

namespace PeluPOS.Views.Servicios
{
    /// <summary>
    /// Lógica de interacción para ServicioDialog.xaml
    /// </summary>
    public partial class ServicioDialog : Window
    {
        public ServicioEditorViewModel VM { get; }
        public string DialogTitle { get; }

        public ServicioDialog(string title, ServicioEditorViewModel vm)
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
            if (!VM.IsValid) return;

            DialogResult = true;
            Close();
        }
    }
}
