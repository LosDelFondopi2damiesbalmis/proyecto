using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Servicios;

namespace PeluPOS.Services.Api
{
    public class ServicioApiService : IServicioApiService
    {
        private readonly ApiClient _apiClient;

        public ServicioApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<ServicioDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<ServicioDto>>("servicios");
            return result ?? new List<ServicioDto>();
        }

        public async Task<ApiMessageDto?> CreateAsync(ServicioDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("servicios", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> UpdateAsync(ServicioDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("servicios", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idServicio)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"servicios/{idServicio}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }
    }
}
