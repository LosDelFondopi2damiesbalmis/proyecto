namespace PeluPOS.Models.ApiDtos.Auth;

public class LoginRequestDto
{
    public string usuario { get; set; } = "";
    public string contrasena { get; set; } = "";
}