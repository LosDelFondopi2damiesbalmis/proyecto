using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;
using PeluPOS.Services.Api.Converters;

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

        // JsonSerializerDefaults.Web uses camelCase property names and
        // case-insensitive deserialization, matching the Java API output.
        // The JavaLocalDateTimeConverter ensures DateTime is serialized as
        // "yyyy-MM-ddTHH:mm:ss" (no fractional seconds / timezone) so that
        // Java JSON-B can parse it with @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss").
        JsonOptions = new JsonSerializerOptions(JsonSerializerDefaults.Web)
        {
            Converters = { new JavaLocalDateTimeConverter() }
        };
    }

    public HttpClient HttpClient => _httpClient;

    /// <summary>
    /// JsonSerializerOptions with the Java-compatible DateTime converter.
    /// Pass these to every PostAsJsonAsync / GetFromJsonAsync / ReadFromJsonAsync call.
    /// </summary>
    public JsonSerializerOptions JsonOptions { get; }

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
