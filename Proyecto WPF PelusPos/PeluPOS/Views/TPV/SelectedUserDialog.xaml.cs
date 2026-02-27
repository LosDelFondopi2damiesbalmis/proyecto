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
using PeluPOS.Models.Entities;
using PeluPOS.ViewModels.TPV;

namespace PeluPOS.Views.TPV
{
    /// <summary>
    /// Lógica de interacción para SelectedUserDialog.xaml
    /// </summary>
    public partial class SelectedUserDialog : Window
    {
        public SelectUserViewModel VM { get; }

        public Empleado? Selected => VM.SelectedUser;

        public SelectedUserDialog(SelectUserViewModel vm)
        {
            InitializeComponent();
            VM = vm;
            DataContext = VM;
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }

        private void Ok_Click(object sender, RoutedEventArgs e)
        {
            if (VM.SelectedUser == null)
            {
                VM.Error = "Selecciona un usuario.";
                return;
            }

            DialogResult = true;
            Close();
        }
    }
}
