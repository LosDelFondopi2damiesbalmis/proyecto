using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface ITpvVentaService
    {
        Task<Factura> CrearFacturaAsync(
            Empleado empleado,
            Cliente cliente,
            string tipoPago,
            bool pendiente,
            IReadOnlyList<LineaFactura> lineas);
    }
}
