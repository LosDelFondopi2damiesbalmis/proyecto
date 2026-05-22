using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using PeluPOS.Services;

namespace PeluPOS.Views.Usuarios
{
    public partial class UsuariosPage : Page
    {
        private readonly ObservableCollection<UsuarioRow> _usuarios = new();
        private List<UsuarioRow> _all = new();

        public UsuariosPage()
        {
            InitializeComponent();
            Loaded += async (_, __) => await CargarDesdeApiAsync();
        }

        private async Task CargarDesdeApiAsync()
        {
            CountText.Text = "Cargando…";

            var dtos = await AppServices.UsuarioApi.GetUsuariosAsync();

            _all = dtos.Select(u => new UsuarioRow
            {
                IdUsuario = u.idUsuario,
                Nombre = u.usuario,
                Empleado = u.idEmpleado?.nombre ?? "-",
                Rol = u.rolUsuario ?? "-",
                Activo = true,
                UltimoAcceso = "-"
            }).ToList();

            RefrescarGrid();
        }

        private void RefrescarGrid(IEnumerable<UsuarioRow>? data = null)
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
                (u.Empleado ?? "").ToLowerInvariant().Contains(q) ||
                (u.Rol ?? "").ToLowerInvariant().Contains(q));

            RefrescarGrid(filtered);
        }

        private void NuevoUsuario_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Aquí abrirías un diálogo o navegarías a la pantalla de alta de usuario.");
        }

        private void Editar_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button btn && btn.Tag is UsuarioRow u)
                MessageBox.Show($"Editar: {u.Nombre}");
        }

        private void Eliminar_Click(object sender, RoutedEventArgs e)
        {
            if (sender is Button btn && btn.Tag is UsuarioRow u)
            {
                var ok = MessageBox.Show($"¿Eliminar a '{u.Nombre}'?", "Confirmar", MessageBoxButton.YesNo, MessageBoxImage.Warning);
                if (ok == MessageBoxResult.Yes)
                {
                    _all.Remove(u);
                    RefrescarGrid();
                }
            }
        }

        private async void Recargar_Click(object sender, RoutedEventArgs e)
        {
            await CargarDesdeApiAsync();
        }

        private void Exportar_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Exportar: aquí generarías Excel/CSV.");
        }
    }

    /// <summary>Fila de visualización mapeada desde <see cref="PeluPOS.Models.ApiDtos.Usuarios.UsuarioDto"/>.</summary>
    public class UsuarioRow
    {
        public long IdUsuario { get; set; }
        public string? Nombre { get; set; }
        public string? Empleado { get; set; }
        public string? Rol { get; set; }
        public bool Activo { get; set; }
        public string? UltimoAcceso { get; set; }
    }
}
