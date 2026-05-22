using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Empleados;

namespace PeluPOS.Services.Api;

public interface IEmpleadoApiService
{
    Task<List<EmpleadoDto>> GetAllAsync();
    Task<EmpleadoVentasResumenDto?> GetVentasResumenAsync(long idEmpleado);
    Task<ApiMessageDto?> CreateAsync(EmpleadoDto dto);
    Task<ApiMessageDto?> UpdateAsync(EmpleadoDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idEmpleado);
}
