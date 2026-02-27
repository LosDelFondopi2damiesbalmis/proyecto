using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class TpvVentaService : ITpvVentaService
    {
        public Task<Factura> CrearFacturaAsync(
            Empleado empleado,
            Cliente? cliente,
            string tipoPago,
            bool pendiente,
            IReadOnlyList<LineaFactura> lineas)
        {
            if (empleado == null) throw new InvalidOperationException("Empleado activo requerido.");
            if (lineas == null || lineas.Count == 0) throw new InvalidOperationException("No hay líneas en el ticket.");
            if (string.IsNullOrWhiteSpace(tipoPago)) tipoPago = "Efectivo";

            // Stock check + descuento
            foreach (var l in lineas)
            {
                if (l.Producto != null)
                {
                    var prod = MockData.Productos.FirstOrDefault(p => p.Id == l.Producto.Id)
                               ?? throw new InvalidOperationException("Producto no encontrado.");

                    if (prod.Stock < l.Cantidad)
                        throw new InvalidOperationException($"Stock insuficiente: {prod.Nombre}");

                    prod.Stock -= l.Cantidad;
                }
            }

            var newId = MockData.Facturas.Any() ? MockData.Facturas.Max(f => f.Id) + 1 : 1;

            var factura = new Factura
            {
                Id = newId,
                Fecha = DateTime.Now,
                TipoPago = tipoPago,
                Pendiente = pendiente,
                Cliente = cliente,
                Empleado = empleado,
                // Monto: suma líneas
                Monto = lineas.Sum(l => l.Cantidad * l.PrecioUnitario)
            };

            foreach (var l in lineas)
            {
                // clonar para no reutilizar referencias del ticket
                factura.Lineas.Add(new LineaFactura
                {
                    Id = NextLineaId(),
                    Cantidad = l.Cantidad,
                    PrecioUnitario = l.PrecioUnitario,
                    Producto = l.Producto,
                    Servicio = l.Servicio
                });
            }

            MockData.Facturas.Add(factura);
            return Task.FromResult(factura);
        }

        private long NextLineaId()
        {
            var all = MockData.Facturas.SelectMany(f => f.Lineas).ToList();
            return all.Any() ? all.Max(l => l.Id) + 1 : 1;
        }
    }
}
