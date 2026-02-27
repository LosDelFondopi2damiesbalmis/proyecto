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
using PeluPOS.Services;

namespace PeluPOS.Views
{
    /// <summary>
    /// Lógica de interacción para LoginDialog.xaml
    /// </summary>
    public partial class LoginDialog : Window
    {
        private readonly IAuthService _auth;
        public LoginViewModel VM { get; }

        public Usuario? SelectedUser => VM.SelectedUser.Usuario;
        public string Password => PasswordBox.Password;

        public LoginDialog(LoginViewModel vm, IAuthService auth)
        {
            InitializeComponent();
            VM = vm;
            _auth = auth;
            DataContext = VM;
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
            Close();
        }

        private async void Ok_Click(object sender, RoutedEventArgs e)
        {
            VM.Error = null;

            if (VM.SelectedUser == null)
            {
                VM.Error = "Selecciona un usuario.";
                return;
            }

            if (string.IsNullOrWhiteSpace(Password))
            {
                VM.Error = "Introduce la contraseña.";
                return;
            }

            var ok = await _auth.ValidatePasswordAsync(VM.SelectedUser.Usuario, Password);
            if (!ok)
            {
                VM.Error = "Contraseña incorrecta.";
                return;
            }

            DialogResult = true;
            Close();
        }
    }
}
