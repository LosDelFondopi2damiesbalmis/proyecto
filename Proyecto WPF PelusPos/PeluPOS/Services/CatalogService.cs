using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services
{
    public class CatalogService : ICatalogService
    {
        private readonly IProductoApiService _productoApi;
        private readonly IServicioApiService _servicioApi;
        private readonly IClienteApiService  _clienteApi;

        public CatalogService(
            IProductoApiService productoApi,
            IServicioApiService servicioApi,
            IClienteApiService  clienteApi)
        {
            _productoApi = productoApi;
            _servicioApi = servicioApi;
            _clienteApi  = clienteApi;
        }

        public async Task<IReadOnlyList<Producto>> GetProductosAsync()
        {
            var dtos = await _productoApi.GetAllAsync();
            return dtos.Select(d => new Producto
            {
                Id           = d.idProducto,
                Nombre       = d.nombre,
                PrecioCompra = d.precioCompra,
                PrecioVenta  = d.precioVenta,
                Stock        = d.stock
            }).ToList();
        }

        public async Task<IReadOnlyList<Servicio>> GetServiciosAsync()
        {
            var dtos = await _servicioApi.GetAllAsync();
            return dtos.Select(d => new Servicio
            {
                Id          = d.idServicio,
                Nombre      = d.nombre,
                Precio      = d.precio,
                Descripcion = d.descripcion ?? string.Empty
            }).ToList();
        }

        public async Task<IReadOnlyList<Cliente>> GetClientesAsync()
        {
            var dtos = await _clienteApi.GetAllAsync();
            return dtos.Select(d => new Cliente
            {
                Id       = d.idCliente,
                Nombre   = d.nombre,
                Telefono = d.telefono ?? 0L,
                Deuda    = d.deuda    ?? 0m
            }).ToList();
        }
    }
}
