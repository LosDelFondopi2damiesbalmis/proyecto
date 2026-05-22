using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Locales;

namespace PeluPOS.Services.Api;

public interface ILocalApiService
{
    Task<List<LocalDto>> GetAllAsync();
    Task<LocalDto?> GetByIdAsync(long idLocal);
    Task<ApiMessageDto?> CreateAsync(LocalDto dto);
    Task<ApiMessageDto?> UpdateAsync(LocalDto dto);
    Task<ApiMessageDto?> DeleteAsync(long idLocal);
}
