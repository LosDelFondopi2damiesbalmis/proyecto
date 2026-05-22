using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Productos;

namespace PeluPOS.Services.Api
{
    public class ProductoApiService : IProductoApiService
    {
        private readonly ApiClient _apiClient;

        public ProductoApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<ProductoDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<ProductoDto>>("productos");
            return result ?? new List<ProductoDto>();
        }

        public async Task<ApiMessageDto?> CreateAsync(ProductoDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("productos", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> UpdateAsync(ProductoDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("productos", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idProducto)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"productos/{idProducto}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }
    }
}
