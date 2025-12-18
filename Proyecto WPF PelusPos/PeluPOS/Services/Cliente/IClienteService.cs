using System.Collections.Generic;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface IClienteService
    {
        Task<IReadOnlyList<Cliente>> GetAllAsync();
    }
}
