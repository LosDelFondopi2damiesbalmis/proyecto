using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.ApiDtos.Empleados;
using PeluPOS.Models.ApiDtos.Facturas;
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
            if (lineas == null || lineas.Count == 0)
                throw new InvalidOperationException("No hay líneas en el ticket.");
            if (string.IsNullOrWhiteSpace(tipoPago))
                tipoPago = "Efectivo";

            // ── PASO 1: Crear la cabecera de la factura (sin líneas) ──────────
            var headerDto = new FacturaDto
            {
                monto      = lineas.Sum(l => l.Cantidad * l.PrecioUnitario),
                fecha      = DateTime.Now,
                pendiente  = pendiente,
                tipoPago   = tipoPago,
                idEmpleado = new EmpleadoDto { idEmpleado = empleadoId },
                idCliente  = cliente != null ? new ClienteDto { idCliente = cliente.Id } : null
            };
            // Las colecciones deben estar vacías para que la API no intente
            // procesar líneas cuyo FK (idFactura) aún no existe.
            headerDto.facturaProductoCollection = new List<FacturaProductoDto>();
            headerDto.facturaServicioCollection  = new List<FacturaServicioDto>();

            var facturaCreada = await _facturaApi.CreateAsync(headerDto)
                ?? throw new InvalidOperationException("La API no devolvió la factura creada.");

            long newIdFactura = facturaCreada.idFactura;

            // ── PASO 2: Crear cada línea de producto ──────────────────────────
            foreach (var l in lineas.Where(x => x.Producto != null))
            {
                var lineaDto = new FacturaProductoDto
                {
                    facturaProductoPK = new FacturaProductoPkDto
                    {
                        idFactura  = newIdFactura,
                        idProducto = l.Producto!.Id
                    },
                    cantidad      = l.Cantidad,
                    precioVendido = l.PrecioUnitario
                    // producto is intentionally omitted: the Java API reconstructs it
                    // from facturaProductoPK.idProducto, so sending a partial object
                    // with null @NotNull fields would cause JSON-B validation errors.
                };
                await _facturaApi.AddProductoAsync(lineaDto);
            }

            // ── PASO 3: Crear cada línea de servicio ──────────────────────────
            foreach (var l in lineas.Where(x => x.Servicio != null))
            {
                var lineaDto = new FacturaServicioDto
                {
                    facturaServicioPK = new FacturaServicioPkDto
                    {
                        idFactura  = newIdFactura,
                        idServicio = l.Servicio!.Id
                    },
                    cantidad      = l.Cantidad,
                    precioCobrado = l.PrecioUnitario
                    // servicio is intentionally omitted: the Java API reconstructs it
                    // from facturaServicioPK.idServicio.
                };
                await _facturaApi.AddServicioAsync(lineaDto);
            }

            // ── Construir la entidad local con el ID real devuelto por la API ─
            var factura = new Factura
            {
                Id        = newIdFactura,
                Fecha     = headerDto.fecha,
                TipoPago  = tipoPago,
                Pendiente = pendiente,
                Cliente   = cliente,
                Empleado  = new Empleado { Id = empleadoId },
                Monto     = headerDto.monto
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
