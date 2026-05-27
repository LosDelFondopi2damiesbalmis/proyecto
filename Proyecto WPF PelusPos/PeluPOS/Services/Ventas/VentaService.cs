using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.Ventas
{
    public class VentaService : IVentaService
    {
        private readonly IFacturaApiService _facturaApi;
        private readonly IProductoApiService _productoApi;
        private readonly IServicioApiService _servicioApi;

        public VentaService(IFacturaApiService facturaApi,
                            IProductoApiService productoApi,
                            IServicioApiService servicioApi)
        {
            _facturaApi  = facturaApi;
            _productoApi = productoApi;
            _servicioApi = servicioApi;
        }

        public async Task<IReadOnlyList<Factura>> GetAllAsync()
        {
            // Fetch everything in parallel
            var dtosTask      = _facturaApi.GetAllAsync();
            var productosTask = _productoApi.GetAllAsync();
            var serviciosTask = _servicioApi.GetAllAsync();
            await Task.WhenAll(dtosTask, productosTask, serviciosTask);

            var dtos      = dtosTask.Result;
            var productos = productosTask.Result.ToDictionary(p => p.idProducto);
            var servicios = serviciosTask.Result.ToDictionary(s => s.idServicio);

            return dtos.Select(f =>
            {
                var lineasProducto = f.facturaProductoCollection.Select(p =>
                {
                    // Prefer the nested object; fall back to the catalogue lookup
                    var idProd = p.producto?.idProducto
                                 ?? p.facturaProductoPK?.idProducto
                                 ?? 0L;
                    productos.TryGetValue(idProd, out var prodDto);
                    var prod = p.producto ?? prodDto;

                    return new LineaFactura
                    {
                        ProductoId     = idProd == 0 ? null : idProd,
                        Producto       = prod is not null ? new Producto
                        {
                            Id          = prod.idProducto,
                            Nombre      = prod.nombre,
                            PrecioVenta = prod.precioVenta,
                            PrecioCompra= prod.precioCompra,
                            Stock       = prod.stock
                        } : null,
                        Cantidad       = p.cantidad ?? 0,
                        PrecioUnitario = p.precioVendido ?? 0m
                    };
                });

                var lineasServicio = f.facturaServicioCollection.Select(s =>
                {
                    var idSvc = s.servicio?.idServicio
                                ?? s.facturaServicioPK?.idServicio
                                ?? 0L;
                    servicios.TryGetValue(idSvc, out var svcDto);
                    var svc = s.servicio ?? svcDto;

                    return new LineaFactura
                    {
                        ServicioId     = idSvc == 0 ? null : idSvc,
                        Servicio       = svc is not null ? new Servicio
                        {
                            Id          = svc.idServicio,
                            Nombre      = svc.nombre,
                            Precio      = svc.precio,
                            Descripcion = svc.descripcion ?? string.Empty
                        } : null,
                        Cantidad       = s.cantidad ?? 0,
                        PrecioUnitario = s.precioCobrado ?? 0m
                    };
                });

                return new Factura
                {
                    Id         = f.idFactura,
                    Monto      = f.monto,
                    Fecha      = f.fecha,
                    Pendiente  = f.pendiente  ?? false,
                    TipoPago   = f.tipoPago   ?? string.Empty,
                    EmpleadoId = f.idEmpleado?.idEmpleado ?? 0L,
                    ClienteId  = f.idCliente?.idCliente   ?? 0L,
                    Cliente    = f.idCliente is not null ? new Cliente
                    {
                        Id     = f.idCliente.idCliente,
                        Nombre = f.idCliente.nombre,
                        Deuda  = f.idCliente.deuda ?? 0m
                    } : null,
                    Empleado   = f.idEmpleado is not null ? new Empleado
                    {
                        Id     = f.idEmpleado.idEmpleado,
                        Nombre = f.idEmpleado.nombre
                    } : null,
                    Lineas     = lineasProducto.Concat(lineasServicio).ToList()
                };
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
