using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class AuthService : IAuthService
    {
        public Task<IReadOnlyList<Usuario>> GetUsersAsync()
        {
            // Si tus usuarios están en MockData.Usuarios úsalo aquí.
            // Si no existe, los sacamos de empleados:
            var usuarios = MockData.Empleados
                .Where(e => e.Usuario != null)
                .Select(e => e.Usuario!)
                .ToList();

            return Task.FromResult((IReadOnlyList<Usuario>)usuarios);
        }

        public Task<Empleado?> ResolveEmpleadoAsync(Usuario usuario)
        {
            // Si Usuario tiene .Empleado, úsalo:
            // return Task.FromResult(usuario.Empleado);

            // Caso común: Usuario tiene EmpleadoId:
            var emp = MockData.Empleados.FirstOrDefault(e => e.Id == usuario.EmpleadoId);
            return Task.FromResult(emp);
        }
    }
}
