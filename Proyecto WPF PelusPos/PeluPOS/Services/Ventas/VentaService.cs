using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.Ventas
{
    public class VentaService : IVentaService
    {
        private readonly IFacturaApiService _facturaApi;

        public VentaService(IFacturaApiService facturaApi)
        {
            _facturaApi = facturaApi;
        }

        public async Task<IReadOnlyList<Factura>> GetAllAsync()
        {
            var dtos = await _facturaApi.GetAllAsync();
            return dtos.Select(f => new Factura
            {
                Id         = f.idFactura,
                Monto      = f.monto,
                Fecha      = f.fecha,
                Pendiente  = f.pendiente  ?? false,
                TipoPago   = f.tipoPago   ?? string.Empty,
                EmpleadoId = f.idEmpleado?.idEmpleado ?? 0L,
                ClienteId  = f.idCliente?.idCliente   ?? 0L
            }).ToList();
        }

        public async Task UpdatePagoAsync(long facturaId, string tipoPago, bool pendiente)
        {
            var dto = await _facturaApi.GetByIdAsync(facturaId);
            if (dto is null) return;

            dto.tipoPago  = tipoPago;
            dto.pendiente = pendiente;
            await _facturaApi.UpdateAsync(dto);
        }
    }
}
