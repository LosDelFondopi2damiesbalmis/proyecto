using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using PeluPOS.Models.ApiDtos.Usuarios;
using PeluPOS.Services;
using PeluPOS.ViewModels.UsuarioPage;

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

        private async void NuevoUsuario_Click(object sender, RoutedEventArgs e)
        {
            var vm = new UsuarioEditorViewModel();
            await CargarEmpleadosEnVm(vm);

            var dlg = new UsuarioDialog("Nuevo usuario", vm, isCreate: true)
            {
                Owner = Window.GetWindow(this)
            };

            if (dlg.ShowDialog() != true) return;

            var req = new CreateUsuarioRequestDto
            {
                usuario = vm.NombreUsuario,
                contrasena = vm.Contrasena,
                rolUsuario = vm.RolSeleccionado!,
                idEmpleado = vm.EmpleadoSeleccionado?.idEmpleado
            };

            var created = await AppServices.UsuarioApi.CreateAsync(req);
            if (created is null)
            {
                MessageBox.Show("Error al crear el usuario.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            await CargarDesdeApiAsync();
        }

        private async void Editar_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not UsuarioRow row) return;

            var vm = new UsuarioEditorViewModel
            {
                NombreUsuario = row.Nombre ?? "",
                RolSeleccionado = row.Rol == "-" ? null : row.Rol
            };
            await CargarEmpleadosEnVm(vm);

            // Pre-seleccionar empleado vinculado si existe
            if (row.Empleado != "-")
                vm.EmpleadoSeleccionado = vm.Empleados.FirstOrDefault(emp => emp.nombre == row.Empleado);

            var dlg = new UsuarioDialog("Editar usuario", vm, isCreate: false)
            {
                Owner = Window.GetWindow(this)
            };

            if (dlg.ShowDialog() != true) return;

            var req = new UpdateUsuarioRequestDto
            {
                idUsuario = row.IdUsuario,
                usuario = vm.NombreUsuario,
                rolUsuario = vm.RolSeleccionado!,
                idEmpleado = vm.EmpleadoSeleccionado?.idEmpleado
            };

            var updated = await AppServices.UsuarioApi.UpdateAsync(req);
            if (updated is null)
            {
                MessageBox.Show("Error al actualizar el usuario.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            await CargarDesdeApiAsync();
        }

        private async void Eliminar_Click(object sender, RoutedEventArgs e)
        {
            if (sender is not Button btn || btn.Tag is not UsuarioRow row) return;

            var ok = MessageBox.Show($"¿Eliminar al usuario '{row.Nombre}'?", "Confirmar",
                MessageBoxButton.YesNo, MessageBoxImage.Warning);
            if (ok != MessageBoxResult.Yes) return;

            var deleted = await AppServices.UsuarioApi.DeleteAsync(row.IdUsuario);
            if (deleted is null)
            {
                MessageBox.Show("Error al eliminar el usuario.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }

            await CargarDesdeApiAsync();
        }

        private async void Recargar_Click(object sender, RoutedEventArgs e)
        {
            await CargarDesdeApiAsync();
        }

        private void Exportar_Click(object sender, RoutedEventArgs e)
        {
            MessageBox.Show("Exportar: aquí generarías Excel/CSV.");
        }

        // ── helpers ──────────────────────────────────────────────────────────

        private static async Task CargarEmpleadosEnVm(UsuarioEditorViewModel vm)
        {
            var empleados = await AppServices.EmpleadoApi.GetAllAsync();
            vm.Empleados.Clear();
            foreach (var emp in empleados)
                vm.Empleados.Add(new EmpleadoMiniDto
                {
                    idEmpleado = emp.idEmpleado,
                    nombre = emp.nombre
                });
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
