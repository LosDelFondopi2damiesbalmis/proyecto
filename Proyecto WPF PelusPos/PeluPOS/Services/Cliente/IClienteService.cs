using System.Collections.Generic;
using System.Threading.Tasks;
using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public interface IClienteService
    {
        Task<IReadOnlyList<Cliente>> GetAllAsync();
        Task<bool> CreateAsync(ClienteDto dto);
        Task<bool> UpdateAsync(ClienteDto dto);
        Task<bool> DeleteAsync(long id);
    }
}
