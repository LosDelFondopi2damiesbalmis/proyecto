using PeluPOS.Models.ApiDtos.Usuarios;

namespace PeluPOS.Services.Api;

public interface IUsuarioApiService
{
    Task<List<UsuarioDto>> GetUsuariosAsync();
}
