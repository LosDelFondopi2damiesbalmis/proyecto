using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class CatalogService : ICatalogService
    {
        public Task<IReadOnlyList<Producto>> GetProductosAsync()
            => Task.FromResult((IReadOnlyList<Producto>)MockData.Productos.ToList());

        public Task<IReadOnlyList<Servicio>> GetServiciosAsync()
            => Task.FromResult((IReadOnlyList<Servicio>)MockData.Servicios.ToList());

        public Task<IReadOnlyList<Cliente>> GetClientesAsync()
            => Task.FromResult((IReadOnlyList<Cliente>)MockData.Clientes.ToList());
    }
}
