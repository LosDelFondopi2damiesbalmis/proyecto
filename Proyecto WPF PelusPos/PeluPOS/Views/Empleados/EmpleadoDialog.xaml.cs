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

namespace PeluPOS.Views.Empleados
{
    /// <summary>
    /// Lógica de interacción para EmpleadoDialog.xaml
    /// </summary>
    public partial class EmpleadoDialog : Window
    {
        public EmpleadoEditorViewModel VM { get; }
        public string DialogTitle { get; }
        public Visibility PasswordVisibility { get; }

        private readonly bool _isCreate;

        public EmpleadoDialog(string title, EmpleadoEditorViewModel vm, bool isCreate)
        {
            InitializeComponent();

            DialogTitle = title;
            VM = vm;
            _isCreate = isCreate;

            PasswordVisibility = isCreate ? Visibility.Visible : Visibility.Collapsed;

            DataContext = this;
        }

        private void Cancelar_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }

        private void Guardar_Click(object sender, RoutedEventArgs e)
        {
            if (_isCreate) VM.ValidateCreate();
            else VM.ValidateEdit();

            if ((_isCreate && !VM.IsValidCreate) || (!_isCreate && !VM.IsValidEdit))
                return;

            DialogResult = true;
            Close();
        }
    }
}
