using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface ITpvVentaService
    {
        Task<Factura> CrearFacturaAsync(
            long empleadoId,
            Cliente? cliente,
            string tipoPago,
            bool pendiente,
            IReadOnlyList<LineaFactura> lineas);
    }
}
