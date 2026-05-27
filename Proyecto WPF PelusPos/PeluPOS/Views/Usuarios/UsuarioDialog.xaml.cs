using System.Windows;
using PeluPOS.ViewModels.UsuarioPage;

namespace PeluPOS.Views.Usuarios
{
    public partial class UsuarioDialog : Window
    {
        public UsuarioEditorViewModel VM { get; }
        public string DialogTitle { get; }
        public Visibility PasswordVisibility { get; }

        private readonly bool _isCreate;

        public UsuarioDialog(string title, UsuarioEditorViewModel vm, bool isCreate)
        {
            InitializeComponent();

            DialogTitle = title;
            VM = vm;
            _isCreate = isCreate;

            PasswordVisibility = isCreate ? Visibility.Visible : Visibility.Collapsed;

            DataContext = this;
        }

        private void PasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {
            VM.Contrasena = PasswordBox.Password;
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
