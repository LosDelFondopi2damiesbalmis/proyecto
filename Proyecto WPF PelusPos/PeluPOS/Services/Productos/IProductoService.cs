using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.ApiDtos.Common;
using PeluPOS.Models.ApiDtos.Productos;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Productos
{
    public interface IProductoService
    {
        Task<IReadOnlyList<Producto>> GetAllAsync();
        Task<Producto> AddAsync(string nombre, decimal precioCompra, decimal precioVenta, long stock);
        Task UpdateAsync(long id, string nombre, decimal precioCompra, decimal precioVenta);
        Task UpdateStockAsync(long id, long newStock);
        Task DeleteAsync(long id);

        Task<IReadOnlyList<Factura>> GetFacturasByProductoAsync(long productoId);
    }
}
