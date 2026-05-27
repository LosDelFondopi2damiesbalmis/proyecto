using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Facturas;

namespace PeluPOS.Services.Api;

public interface IFacturaApiService
{
    Task<List<FacturaDto>> GetAllAsync();
    Task<FacturaDto?> GetByIdAsync(long idFactura);
    Task<FacturaDto?> CreateAsync(FacturaDto dto);
    Task<ApiMessageDto?> UpdateAsync(FacturaDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idFactura);
    Task<ApiMessageDto?> AddProductoAsync(FacturaProductoDto dto);
    Task<ApiMessageDto?> AddServicioAsync(FacturaServicioDto dto);
}
