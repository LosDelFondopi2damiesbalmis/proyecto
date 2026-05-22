using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Locales;

namespace PeluPOS.Services.Api
{
    public class LocalApiService : ILocalApiService
    {
        private readonly ApiClient _apiClient;

        public LocalApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<LocalDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<LocalDto>>("locales");
            return result ?? new List<LocalDto>();
        }

        public async Task<LocalDto?> GetByIdAsync(long idLocal)
        {
            return await _apiClient.HttpClient.GetFromJsonAsync<LocalDto>($"locales/{idLocal}");
        }

        public async Task<ApiMessageDto?> CreateAsync(LocalDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("locales", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> UpdateAsync(LocalDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("locales", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idLocal)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"locales/{idLocal}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }
    }
}
