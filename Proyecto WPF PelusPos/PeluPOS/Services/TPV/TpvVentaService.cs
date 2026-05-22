using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.ApiDtos.Empleados;
using PeluPOS.Models.ApiDtos.Facturas;
using PeluPOS.Models.ApiDtos.Productos;
using PeluPOS.Models.ApiDtos.Servicios;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.TPV
{
    public class TpvVentaService : ITpvVentaService
    {
        private readonly IFacturaApiService _facturaApi;

        public TpvVentaService(IFacturaApiService facturaApi)
        {
            _facturaApi = facturaApi;
        }

        public async Task<Factura> CrearFacturaAsync(
            long empleadoId,
            Cliente? cliente,
            string tipoPago,
            bool pendiente,
            IReadOnlyList<LineaFactura> lineas)
        {
            if (lineas == null || lineas.Count == 0) throw new InvalidOperationException("No hay líneas en el ticket.");
            if (string.IsNullOrWhiteSpace(tipoPago)) tipoPago = "Efectivo";

            var dto = new FacturaDto
            {
                monto      = lineas.Sum(l => l.Cantidad * l.PrecioUnitario),
                fecha      = DateTime.Now,
                pendiente  = pendiente,
                tipoPago   = tipoPago,
                idEmpleado = new EmpleadoDto { idEmpleado = empleadoId },
                idCliente  = cliente != null ? new ClienteDto { idCliente = cliente.Id } : null
            };

            foreach (var l in lineas)
            {
                if (l.Producto != null)
                {
                    dto.facturaProductoCollection.Add(new FacturaProductoDto
                    {
                        facturaProductoPK = new FacturaProductoPkDto { idProducto = l.Producto.Id },
                        cantidad          = l.Cantidad,
                        precioVendido     = l.PrecioUnitario,
                        producto          = new ProductoDto { idProducto = l.Producto.Id, nombre = l.Producto.Nombre }
                    });
                }
                else if (l.Servicio != null)
                {
                    dto.facturaServicioCollection.Add(new FacturaServicioDto
                    {
                        facturaServicioPK = new FacturaServicioPkDto { idServicio = l.Servicio.Id },
                        cantidad          = l.Cantidad,
                        precioCobrado     = l.PrecioUnitario,
                        servicio          = new ServicioDto { idServicio = l.Servicio.Id, nombre = l.Servicio.Nombre }
                    });
                }
            }

            await _facturaApi.CreateAsync(dto);

            var factura = new Factura
            {
                Id        = 0,
                Fecha     = dto.fecha,
                TipoPago  = tipoPago,
                Pendiente = pendiente,
                Cliente   = cliente,
                Empleado  = new Empleado { Id = empleadoId },
                Monto     = dto.monto
            };

            foreach (var l in lineas)
            {
                factura.Lineas.Add(new LineaFactura
                {
                    Id             = 0,
                    Cantidad       = l.Cantidad,
                    PrecioUnitario = l.PrecioUnitario,
                    Producto       = l.Producto,
                    Servicio       = l.Servicio
                });
            }

            return factura;
        }
    }
}
