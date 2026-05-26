using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Usuarios;

namespace PeluPOS.Services.Api;

public class UsuarioApiService : IUsuarioApiService
{
    private readonly ApiClient _apiClient;

    public UsuarioApiService(ApiClient apiClient)
    {
        _apiClient = apiClient;
    }

    public async Task<List<UsuarioDto>> GetUsuariosAsync()
    {
        var result = await _apiClient.HttpClient.GetFromJsonAsync<List<UsuarioDto>>("usuarios");
        return result ?? new List<UsuarioDto>();
    }

    public async Task<ApiMessageDto?> CreateAsync(CreateUsuarioRequestDto dto)
    {
        var response = await _apiClient.HttpClient.PostAsJsonAsync("usuarios", dto);
        return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
    }

    public async Task<ApiMessageDto?> UpdateAsync(UpdateUsuarioRequestDto dto)
    {
        var response = await _apiClient.HttpClient.PutAsJsonAsync("usuarios", dto);
        return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
    }

    public async Task<ApiMessageDto?> DeleteAsync(long idUsuario)
    {
        var response = await _apiClient.HttpClient.DeleteAsync($"usuarios/{idUsuario}");
        return await response.Content.ReadFromJsonAsync<ApiMessageDto>();
    }
}
