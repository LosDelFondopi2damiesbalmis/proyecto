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
using PeluPOS.ViewModels;

namespace PeluPOS.Views.Locales
{
    /// <summary>
    /// Lógica de interacción para NuevoLocalDialog.xaml
    /// </summary>
    public partial class NuevoLocalDialog : Window
    {
        public NuevoLocalViewModel VM { get; }

        public NuevoLocalDialog(NuevoLocalViewModel vm, string titulo = "Crear nuevo local")
        {
            InitializeComponent();
            VM = vm;
            VM.Titulo = titulo;
            DataContext = VM;
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
