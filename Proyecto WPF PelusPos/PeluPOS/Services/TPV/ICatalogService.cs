using PeluPOS.Models.Entities;

namespace PeluPOS.Services.TPV
{
    public interface ICatalogService
    {
        Task<IReadOnlyList<Producto>> GetProductosAsync();
        Task<IReadOnlyList<Servicio>> GetServiciosAsync();
        Task<IReadOnlyList<Cliente>> GetClientesAsync();
    }
}
