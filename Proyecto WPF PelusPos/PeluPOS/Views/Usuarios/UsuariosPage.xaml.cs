using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace PeluPOS.Views.Usuarios
{
    /// <summary>
    /// Lógica de interacción para UsuariosPage.xaml
    /// </summary>
    public partial class UsuariosPage : Page
    {
        private readonly ObservableCollection<UsuarioDto> _usuarios = new();
        private List<UsuarioDto> _all = new();

        public UsuariosPage()
        {
            InitializeComponent();
            CargarDemo();
            RefrescarGrid();
        }

        private void CargarDemo()
        {
            _all = new List<UsuarioDto>
            {
                new UsuarioDto { Nombre="Admin", Email="admin@pelupos.com", Rol="Administrador", Activo=true,  UltimoAcceso=DateTime.Now.AddHours(-2).ToString("dd/MM/yyyy HH:mm") },
                new UsuarioDto { Nombre="Laura Pérez", Email="laura@pelupos.com", Rol="Gerente",         Activo=true,  UltimoAcceso=DateTime.Now.AddDays(-1).ToString("dd/MM/yyyy HH:mm") },
                new UsuarioDto { Nombre="Mario López", Email="mario@pelupos.com", Rol="Empleado",        Activo=false, UltimoAcceso=DateTime.Now.AddDays(-10).ToString("dd/MM/yyyy HH:mm") },
                new UsuarioDto { Nombre="Sara Martín", Email="sara@pelupos.com", Rol="Empleado",         Activo=true,  UltimoAcceso=DateTime.Now.AddMinutes(-35).ToString("dd/MM/yyyy HH:mm") },
            };
        }

        private void RefrescarGrid(IEnumerable<UsuarioDto>? data = null)
        {
            _usuarios.Clear();
            foreach (var u in (data ?? _all))
                _usuarios.Add(u);

            UsuariosGrid.ItemsSource = _usuarios;
            CountText.Text = $"{_usuarios.Count} usuarios";
        }

        private void SearchBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            var q = (SearchBox.Text ?? "").Trim().ToLowerInvariant();

            if (string.IsNullOrWhiteSpace(q))
            {
                RefrescarGrid();
                return;
            }

            var filtered = _all.Where(u =>
                (u.Nombre ?? "").ToLowerInvariant().Contains(q) ||
                (u.Email ?? "").ToLowerInvariant().Contains(q) ||
                (u.Rol ?? "").ToLowerInvariant().Contains(q));

            RefrescarGrid(filtered);
        }

        private void NuevoUsuario_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Aquí abrirías un diálogo o navegarías a la pantalla de alta de usuario.");
        }

        private void Editar_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button btn && btn.Tag is UsuarioDto u)
                MessageBox.Show($"Editar: {u.Nombre}");
        }

        private void Eliminar_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button btn && btn.Tag is UsuarioDto u)
            {
                var ok = MessageBox.Show($"¿Eliminar a '{u.Nombre}'?", "Confirmar", MessageBoxButton.YesNo, MessageBoxImage.Warning);
                if (ok == MessageBoxResult.Yes)
                {
                    _all.Remove(u);
                    RefrescarGrid();
                }
            }
        }

        private void Recargar_Click(object sender, RoutedEventArgs e)
        {
            // Aquí llamarías a tu BBDD / API y recargarías
            RefrescarGrid();
        }

        private void Exportar_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Exportar: aquí generarías Excel/CSV.");
        }
    }

    public class UsuarioDto
    {
        public string? Nombre { get; set; }
        public string? Email { get; set; }
        public string? Rol { get; set; }
        public bool Activo { get; set; }
        public string? UltimoAcceso { get; set; }
    }
}
