using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.ApiDtos.Common;

namespace PeluPOS.Services.Api;

public interface IClienteApiService
{
    Task<List<ClienteDto>> GetAllAsync();
    Task<ClienteDto?> GetByIdAsync(long idCliente);
    Task<ApiMessageDto?> CreateAsync(ClienteDto dto);
    Task<ApiMessageDto?> UpdateAsync(ClienteDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idCliente);
}
