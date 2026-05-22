using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Servicios;

namespace PeluPOS.Services.Api;

public interface IServicioApiService
{
    Task<List<ServicioDto>> GetAllAsync();
    Task<ApiMessageDto?> CreateAsync(ServicioDto dto);
    Task<ApiMessageDto?> UpdateAsync(ServicioDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idServicio);
}
