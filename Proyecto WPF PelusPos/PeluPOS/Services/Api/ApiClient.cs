using System.Net.Http.Headers;

namespace PeluPOS.Services.Api;

public class ApiClient
{
    private readonly HttpClient _httpClient;

    public ApiClient(string baseUrl)
    {
        _httpClient = new HttpClient
        {
            BaseAddress = new Uri(baseUrl)
        };
    }

    public HttpClient HttpClient => _httpClient;

    public void SetBearerToken(string? token)
    {
        if (string.IsNullOrWhiteSpace(token))
        {
            _httpClient.DefaultRequestHeaders.Authorization = null;
            return;
        }

        _httpClient.DefaultRequestHeaders.Authorization =
            new AuthenticationHeaderValue("Bearer", token);
    }
}