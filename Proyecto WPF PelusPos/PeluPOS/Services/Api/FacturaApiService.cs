using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Facturas;

namespace PeluPOS.Services.Api
{
    public class FacturaApiService
    {
        private readonly ApiClient _apiClient;

        public FacturaApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<FacturaDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<FacturaDto>>("facturas");
            return result ?? new List<FacturaDto>();
        }

        public async Task<FacturaDto?> GetByIdAsync(long idFactura)
        {
            return await _apiClient.HttpClient.GetFromJsonAsync<FacturaDto>($"facturas/{idFactura}");
        }

        public async Task<ApiMessageDto?> CreateAsync(FacturaDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("facturas", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> UpdateAsync(FacturaDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("facturas", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idFactura)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"facturas/{idFactura}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }
    }
}