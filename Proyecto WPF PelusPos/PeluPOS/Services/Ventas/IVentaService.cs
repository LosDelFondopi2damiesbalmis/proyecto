using System.Collections.Generic;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Ventas
{
    public interface IVentaService
    {
        Task<IReadOnlyList<Factura>> GetAllAsync();
        Task UpdatePagoAsync(long facturaId, string tipoPago, bool pendiente);
    }
}
