using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface IEmpleadoService
    {
        Task<IReadOnlyList<Empleado>> GetAllAsync();
        Task<Empleado?> GetByIdAsync(long empleadoId);

        Task<IReadOnlyList<Local>> GetLocalesAsync();

        Task<Empleado> CreateAsync(
            string nombre,
            long telefono,
            string email,
            string cargo,
            long localId,
            string password
        );

        Task UpdateAsync(
            long empleadoId,
            string nombre,
            long telefono,
            string email,
            string cargo,
            long localId
        );

        Task DeleteAsync(long empleadoId);

        Task<IReadOnlyList<Factura>> GetFacturasByEmpleadoAsync(long empleadoId);

    }
}
