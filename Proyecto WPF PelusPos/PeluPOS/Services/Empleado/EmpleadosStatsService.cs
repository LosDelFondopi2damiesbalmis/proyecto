using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class EmpleadosStatsService : IEmpleadoStatsService
    {
        public Task<string> GetEmpleadoNombreAsync(long empleadoId)
        {
            var nombre = MockData.Empleados.FirstOrDefault(e => e.Id == empleadoId)?.Nombre ?? "Empleado";
            return Task.FromResult(nombre);
        }

        public Task<IReadOnlyList<Factura>> GetFacturasByEmpleadoAsync(long empleadoId)
        {
            var facturas = MockData.Facturas
                .Where(f => f.Empleado?.Id == empleadoId)
                .OrderByDescending(f => f.Fecha)
                .ToList();

            return Task.FromResult((IReadOnlyList<Factura>)facturas);
        }

    }
}
