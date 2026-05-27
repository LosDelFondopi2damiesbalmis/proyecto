using System.Net.Http.Json;
using System.Text.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Facturas;

namespace PeluPOS.Services.Api
{
    public class FacturaApiService : IFacturaApiService
    {
        private readonly ApiClient _apiClient;
        private JsonSerializerOptions JsonOpts => _apiClient.JsonOptions;

        public FacturaApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<FacturaDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<FacturaDto>>("facturas", JsonOpts);
            return result ?? new List<FacturaDto>();
        }

        public async Task<FacturaDto?> GetByIdAsync(long idFactura)
        {
            return await _apiClient.HttpClient.GetFromJsonAsync<FacturaDto>($"facturas/{idFactura}", JsonOpts);
        }

        public async Task<FacturaDto?> CreateAsync(FacturaDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("facturas", dto, JsonOpts);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<FacturaDto>(JsonOpts);
        }

        public async Task<ApiMessageDto?> AddProductoAsync(FacturaProductoDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("facturaproductos", dto, JsonOpts);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>(JsonOpts);
        }

        public async Task<ApiMessageDto?> AddServicioAsync(FacturaServicioDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("facturaservicios", dto, JsonOpts);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>(JsonOpts);
        }

        public async Task<ApiMessageDto?> UpdateAsync(FacturaDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("facturas", dto, JsonOpts);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>(JsonOpts);
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idFactura)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"facturas/{idFactura}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>(JsonOpts);
        }
    }
}
