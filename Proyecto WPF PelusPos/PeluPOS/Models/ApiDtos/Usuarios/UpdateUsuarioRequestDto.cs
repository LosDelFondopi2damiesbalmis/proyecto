namespace PeluPOS.Models.ApiDtos.Usuarios;

public class UpdateUsuarioRequestDto
{
    public long idUsuario { get; set; }
    public string usuario { get; set; } = "";
    public string? rolUsuario { get; set; }
    public long? idEmpleado { get; set; }
}
