namespace PeluPOS.Models.ApiDtos.Auth;

public class LoginResponseDto
{
    public string? jwtToken { get; set; }
    public string? idUsuario { get; set; }
    public string? usuario { get; set; }
    public string? rolUsuario { get; set; }
    public string? idEmpleado { get; set; }
    public string? mensaje { get; set; }
}