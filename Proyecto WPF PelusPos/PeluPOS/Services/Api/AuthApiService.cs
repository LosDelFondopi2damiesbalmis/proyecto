using System.Net.Http.Json;
using PeluPOS.Models.ApiDtos.Auth;

namespace PeluPOS.Services.Api;

public class AuthApiService
{
    private readonly ApiClient _apiClient;

    public AuthApiService(ApiClient apiClient)
    {
        _apiClient = apiClient;
    }

    public async Task<LoginResponseDto?> LoginAsync(LoginRequestDto request)
    {
        var response = await _apiClient.HttpClient.PostAsJsonAsync("auth/login", request);

        if (!response.IsSuccessStatusCode)
        {
            var error = await response.Content.ReadFromJsonAsync<LoginResponseDto>();
            return error;
        }

        return await response.Content.ReadFromJsonAsync<LoginResponseDto>();
    }

    public async Task<bool> LogoutAsync()
    {
        var response = await _apiClient.HttpClient.PostAsync("auth/logout", null);
        return response.IsSuccessStatusCode;
    }
}