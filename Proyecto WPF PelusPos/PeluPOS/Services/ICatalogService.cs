using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface ICatalogService
    {
        Task<IReadOnlyList<Producto>> GetProductosAsync();
        Task<IReadOnlyList<Servicio>> GetServiciosAsync();
        Task<IReadOnlyList<Cliente>> GetClientesAsync();
    }
}
