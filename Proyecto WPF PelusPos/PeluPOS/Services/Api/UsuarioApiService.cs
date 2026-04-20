using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Usuarios;

namespace PeluPOS.Services.Api;

public class UsuarioApiService
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
}