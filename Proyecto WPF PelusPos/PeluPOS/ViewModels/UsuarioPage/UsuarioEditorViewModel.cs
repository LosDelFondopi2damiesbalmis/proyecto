using System.Collections.ObjectModel;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.ApiDtos.Usuarios;

namespace PeluPOS.ViewModels.UsuarioPage;

public partial class UsuarioEditorViewModel : ObservableObject
{
    [ObservableProperty] private string nombreUsuario = string.Empty;

    // Solo CREAR
    [ObservableProperty] private string contrasena = string.Empty;

    [ObservableProperty] private string? rolSeleccionado;
    [ObservableProperty] private EmpleadoMiniDto? empleadoSeleccionado;
    [ObservableProperty] private string? error;

    public ObservableCollection<string> Roles { get; } = new()
    {
        "Administrador", "Manager", "Empleado"
    };

    public ObservableCollection<EmpleadoMiniDto> Empleados { get; } = new();

    public bool IsValidCreate =>
        !string.IsNullOrWhiteSpace(NombreUsuario) &&
        !string.IsNullOrWhiteSpace(Contrasena) &&
        !string.IsNullOrWhiteSpace(RolSeleccionado);

    public bool IsValidEdit =>
        !string.IsNullOrWhiteSpace(NombreUsuario) &&
        !string.IsNullOrWhiteSpace(RolSeleccionado);

    public void ValidateCreate()
    {
        if (string.IsNullOrWhiteSpace(NombreUsuario)) Error = "El nombre de usuario es obligatorio.";
        else if (string.IsNullOrWhiteSpace(Contrasena)) Error = "La contraseña es obligatoria.";
        else if (string.IsNullOrWhiteSpace(RolSeleccionado)) Error = "Selecciona un rol.";
        else Error = null;
    }

    public void ValidateEdit()
    {
        if (string.IsNullOrWhiteSpace(NombreUsuario)) Error = "El nombre de usuario es obligatorio.";
        else if (string.IsNullOrWhiteSpace(RolSeleccionado)) Error = "Selecciona un rol.";
        else Error = null;
    }
}
