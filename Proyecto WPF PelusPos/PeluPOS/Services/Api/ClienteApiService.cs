using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.ApiDtos.Common;

namespace PeluPOS.Services.Api
{
    public class ClienteApiService
    {
        private readonly ApiClient _apiClient;

        public ClienteApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<ClienteDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<ClienteDto>>("clientes");
            return result ?? new List<ClienteDto>();
        }

        public async Task<ClienteDto?> GetByIdAsync(long idCliente)
        {
            return await _apiClient.HttpClient.GetFromJsonAsync<ClienteDto>($"clientes/{idCliente}");
        }

        public async Task<ApiMessageDto?> CreateAsync(ClienteDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("clientes", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> UpdateAsync(ClienteDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("clientes", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idCliente)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"clientes/{idCliente}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }
    }
}