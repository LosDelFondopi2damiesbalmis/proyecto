using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Usuarios;

namespace PeluPOS.Services.Api;

public interface IUsuarioApiService
{
    Task<List<UsuarioDto>> GetUsuariosAsync();
    Task<ApiMessageDto?> CreateAsync(CreateUsuarioRequestDto dto);
    Task<ApiMessageDto?> UpdateAsync(UpdateUsuarioRequestDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idUsuario);
}
