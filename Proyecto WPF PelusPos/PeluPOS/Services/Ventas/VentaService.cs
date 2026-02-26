using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Ventas
{
    public class MockVentaService : IVentaService
    {
        public Task<IReadOnlyList<Factura>> GetAllAsync()
        {
            var facturas = MockData.Facturas
                .OrderByDescending(f => f.Fecha)
                .ToList();

            return Task.FromResult((IReadOnlyList<Factura>)facturas);
        }

        public Task UpdatePagoAsync(long facturaId, string tipoPago, bool pendiente)
        {
            var f = MockData.Facturas.FirstOrDefault(x => x.Id == facturaId)
                    ?? throw new InvalidOperationException("Factura no encontrada.");

            if (string.IsNullOrWhiteSpace(tipoPago))
                throw new ArgumentException("TipoPago es obligatorio.", nameof(tipoPago));

            f.TipoPago = tipoPago.Trim();
            f.Pendiente = pendiente;

            return Task.CompletedTask;
        }
    }
}
