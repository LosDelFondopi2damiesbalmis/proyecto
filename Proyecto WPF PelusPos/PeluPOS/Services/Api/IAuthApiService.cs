using PeluPOS.Models.ApiDtos.Auth;

namespace PeluPOS.Services.Api;

public interface IAuthApiService
{
    Task<LoginResponseDto?> LoginAsync(LoginRequestDto request);
    Task<bool> LogoutAsync();
}
