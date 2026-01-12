using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface IEmpleadoStatsService
    {
        Task<string> GetEmpleadoNombreAsync(long empleadoId);
        Task<IReadOnlyList<Factura>> GetFacturasByEmpleadoAsync(long empleadoId);
    }
}
