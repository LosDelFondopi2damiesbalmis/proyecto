using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Auth
{
    public interface IAuthService
    {
        Task<IReadOnlyList<Usuario>> GetUsersAsync();
        Task<Empleado?> ResolveEmpleadoAsync(Usuario usuario);
        Task<bool> ValidatePasswordAsync(Usuario usuario, string password);
    }
}
