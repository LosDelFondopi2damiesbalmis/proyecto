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
using PeluPOS.Services.Ventas;

namespace PeluPOS.Views.Ventas
{
    /// <summary>
    /// Lógica de interacción para VentaDetalleDialog.xaml
    /// </summary>
    public partial class VentaDetalleDialog : Window
    {
        public VentaDetalleViewModel VM { get; }

        public VentaDetalleDialog(VentaDetalleViewModel vm)
        {
            InitializeComponent();
            VM = vm;
            DataContext = VM;
        }

        private void Cerrar_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }

        private void Guardar_Click(object sender, RoutedEventArgs e)
        {
            // Solo TipoPago y Pendiente se guardarán al cerrar (el Page lo aplica con el service)
            if (string.IsNullOrWhiteSpace(VM.TipoPago))
                return;

            DialogResult = true;
            Close();
        }
    }
}
