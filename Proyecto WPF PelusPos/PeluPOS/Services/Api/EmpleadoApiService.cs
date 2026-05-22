using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Empleados;

namespace PeluPOS.Services.Api
{
public class EmpleadoApiService : IEmpleadoApiService
    {
        private readonly ApiClient _apiClient;

        public EmpleadoApiService(ApiClient apiClient)
        {
            _apiClient = apiClient;
        }

        public async Task<List<EmpleadoDto>> GetAllAsync()
        {
            var result = await _apiClient.HttpClient.GetFromJsonAsync<List<EmpleadoDto>>("empleados");
            return result ?? new List<EmpleadoDto>();
        }

        public async Task<EmpleadoVentasResumenDto?> GetVentasResumenAsync(long idEmpleado)
        {
            return await _apiClient.HttpClient.GetFromJsonAsync<EmpleadoVentasResumenDto>($"empleados/ventas/{idEmpleado}");
        }

        public async Task<ApiMessageDto?> CreateAsync(EmpleadoDto dto)
        {
            var response = await _apiClient.HttpClient.PostAsJsonAsync("empleados", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> UpdateAsync(EmpleadoDto dto)
        {
            var response = await _apiClient.HttpClient.PutAsJsonAsync("empleados", dto);
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }

        public async Task<ApiMessageDto?> DeleteAsync(long idEmpleado)
        {
            var response = await _apiClient.HttpClient.DeleteAsync($"empleados/{idEmpleado}");
            return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
        }
    }
}
