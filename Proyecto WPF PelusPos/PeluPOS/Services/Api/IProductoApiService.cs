using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Productos;

namespace PeluPOS.Services.Api;

public interface IProductoApiService
{
    Task<List<ProductoDto>> GetAllAsync();
    Task<ApiMessageDto?> CreateAsync(ProductoDto dto);
    Task<ApiMessageDto?> UpdateAsync(ProductoDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idProducto);
}
