namespace PeluPOS.Models.ApiDtos.Usuarios;

public class UsuarioDto
{
    public long idUsuario { get; set; }
    public string usuario { get; set; } = "";
    public string contrasena { get; set; } = "";
    public string? rolUsuario { get; set; }
    public EmpleadoMiniDto? idEmpleado { get; set; }
}