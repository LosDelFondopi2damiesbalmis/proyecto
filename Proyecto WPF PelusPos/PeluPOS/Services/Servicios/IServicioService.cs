using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Servicios
{
    public interface IServicioService
    {
        Task<IReadOnlyList<Servicio>> GetAllAsync();
        Task<Servicio> AddAsync(string nombre, decimal precio, string descripcion);
        Task UpdateAsync(long id, string nombre, decimal precio, string descripcion);
        Task DeleteAsync(long id);

        Task<IReadOnlyList<Factura>> GetFacturasByServicioAsync(long servicioId);
        Task<IReadOnlyList<Servicio>> GetServiciosRelacionadosAsync(long servicioId);
    }
}
