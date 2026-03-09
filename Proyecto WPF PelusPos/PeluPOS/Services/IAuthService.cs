using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface IAuthService
    {
        Task<IReadOnlyList<Usuario>> GetUsersAsync();
        Task<Empleado?> ResolveEmpleadoAsync(Usuario usuario);

        Task<bool> ValidatePasswordAsync(Usuario usuario, string password);
    }
}
