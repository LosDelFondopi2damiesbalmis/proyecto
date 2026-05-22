using PeluPOS.Models.ApiDtos.Productos;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.Productos
{
    public class ProductoService : IProductoService
    {
        private readonly IProductoApiService _productoApi;
        private readonly IFacturaApiService  _facturaApi;

        public ProductoService(IProductoApiService productoApi, IFacturaApiService facturaApi)
        {
            _productoApi = productoApi;
            _facturaApi  = facturaApi;
        }

        public async Task<IReadOnlyList<Producto>> GetAllAsync()
        {
            var dtos = await _productoApi.GetAllAsync();
            return dtos.Select(ToEntity).ToList();
        }

        public async Task<Producto> AddAsync(string nombre, decimal precioCompra, decimal precioVenta, long stock)
        {
            var dto = new ProductoDto
            {
                nombre       = nombre,
                precioCompra = precioCompra,
                precioVenta  = precioVenta,
                stock        = (int)stock
            };
            await _productoApi.CreateAsync(dto);

            // Retrieve the newly created product by matching name
            var all = await _productoApi.GetAllAsync();
            var created = all.LastOrDefault(p => p.nombre == nombre)
                          ?? all.Last();
            return ToEntity(created);
        }

        public async Task UpdateAsync(long id, string nombre, decimal precioCompra, decimal precioVenta)
        {
            var dto = new ProductoDto
            {
                idProducto   = id,
                nombre       = nombre,
                precioCompra = precioCompra,
                precioVenta  = precioVenta
            };
            await _productoApi.UpdateAsync(dto);
        }

        public async Task UpdateStockAsync(long id, long newStock)
        {
            var all = await _productoApi.GetAllAsync();
            var existing = all.FirstOrDefault(p => p.idProducto == id);
            if (existing is null) return;

            existing.stock = (int)newStock;
            await _productoApi.UpdateAsync(existing);
        }

        public async Task DeleteAsync(long id)
        {
            await _productoApi.DeleteAsync(id);
        }

        public async Task<IReadOnlyList<Factura>> GetFacturasByProductoAsync(long productoId)
        {
            var all = await _facturaApi.GetAllAsync();
            return all
                .Where(f => f.facturaProductoCollection
                    .Any(fp => fp.facturaProductoPK?.idProducto == productoId))
                .Select(f => new Factura
                {
                    Id         = f.idFactura,
                    Monto      = f.monto,
                    Fecha      = f.fecha,
                    Pendiente  = f.pendiente ?? false,
                    TipoPago   = f.tipoPago  ?? string.Empty,
                    ClienteId  = f.idCliente?.idCliente  ?? 0,
                    EmpleadoId = f.idEmpleado?.idEmpleado ?? 0
                })
                .ToList();
        }

        private static Producto ToEntity(ProductoDto d) => new Producto
        {
            Id           = d.idProducto,
            Nombre       = d.nombre       ?? string.Empty,
            PrecioCompra = d.precioCompra,
            PrecioVenta  = d.precioVenta,
            Stock        = d.stock
        };
    }
}
