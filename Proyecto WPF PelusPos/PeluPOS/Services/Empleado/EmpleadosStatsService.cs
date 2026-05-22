using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services
{
    public class EmpleadosStatsService : IEmpleadoStatsService
    {
        private readonly IEmpleadoApiService _empleadoApi;
        private readonly IFacturaApiService  _facturaApi;

        public EmpleadosStatsService(
            IEmpleadoApiService empleadoApi,
            IFacturaApiService  facturaApi)
        {
            _empleadoApi = empleadoApi;
            _facturaApi  = facturaApi;
        }

        public async Task<string> GetEmpleadoNombreAsync(long empleadoId)
        {
            var all = await _empleadoApi.GetAllAsync();
            return all.FirstOrDefault(e => e.idEmpleado == empleadoId)?.nombre ?? string.Empty;
        }

        public async Task<IReadOnlyList<Factura>> GetFacturasByEmpleadoAsync(long empleadoId)
        {
            var all = await _facturaApi.GetAllAsync();
            return all
                .Where(f => f.idEmpleado?.idEmpleado == empleadoId)
                .Select(f => new Factura
                {
                    Id         = f.idFactura,
                    Monto      = f.monto,
                    Fecha      = f.fecha,
                    Pendiente  = f.pendiente  ?? false,
                    TipoPago   = f.tipoPago   ?? string.Empty,
                    EmpleadoId = f.idEmpleado?.idEmpleado ?? 0L,
                    ClienteId  = f.idCliente?.idCliente   ?? 0L
                })
                .ToList();
        }
    }
}
