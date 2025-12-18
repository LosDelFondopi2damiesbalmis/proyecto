using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Productos
{
    public class ProductoService : IProductoService
    {
        public Task<IReadOnlyList<Producto>> GetAllAsync() => Task.FromResult((IReadOnlyList<Producto>)MockData.Productos);

        public Task<Producto> AddAsync(string nombre, decimal precioCompra, decimal precioVenta, long stock)
        {
            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre es obligatorio.", nameof(nombre));
            if (precioCompra < 0 || precioVenta < 0)
                throw new ArgumentException("Los precios no pueden ser negativos.");
            if (stock < 0)
                throw new ArgumentException("El stock no puede ser negativo.", nameof(stock));

            var newId = MockData.Productos.Any() ? MockData.Productos.Max(p => p.Id) + 1 : 1;

            var producto = new Producto
            {
                Id = newId,
                Nombre = nombre.Trim(),
                PrecioCompra = precioCompra,
                PrecioVenta = precioVenta,
                Stock = stock
            };

            MockData.Productos.Add(producto);
            return Task.FromResult(producto);
        }

        public Task UpdateAsync(long id, string nombre, decimal precioCompra, decimal precioVenta)
        {
            var p = MockData.Productos.FirstOrDefault(x => x.Id == id)
                    ?? throw new InvalidOperationException("Producto no encontrado.");

            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre es obligatorio.", nameof(nombre));
            if (precioCompra < 0 || precioVenta < 0)
                throw new ArgumentException("Los precios no pueden ser negativos.");

            p.Nombre = nombre.Trim();
            p.PrecioCompra = precioCompra;
            p.PrecioVenta = precioVenta;

            return Task.CompletedTask;
        }

        public Task UpdateStockAsync(long id, long newStock)
        {
            var p = MockData.Productos.FirstOrDefault(x => x.Id == id)
                    ?? throw new InvalidOperationException("Producto no encontrado.");

            if (newStock < 0)
                throw new ArgumentException("El stock no puede ser negativo.", nameof(newStock));

            p.Stock = newStock;
            return Task.CompletedTask;
        }

        public Task DeleteAsync(long id)
        {
            var p = MockData.Productos.FirstOrDefault(x => x.Id == id);
            if (p != null) MockData.Productos.Remove(p);
            return Task.CompletedTask;
        }

        public Task<IReadOnlyList<Factura>> GetFacturasByProductoAsync(long productoId)
        {
            // Importante: en tu mock sueles setear LineaFactura.Producto = Productos[x]
            // por eso filtramos por l.Producto?.Id (más robusto que ProductoId)
            var facturas = MockData.Facturas
                .Where(f => f.Lineas.Any(l => l.Producto?.Id == productoId))
                .OrderByDescending(f => f.Fecha)
                .ToList();

            return Task.FromResult((IReadOnlyList<Factura>)facturas);
        }
    }
}
