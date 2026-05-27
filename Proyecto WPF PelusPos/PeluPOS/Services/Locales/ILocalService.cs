using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Locales
{
    public interface ILocalService
    {
        Task<IReadOnlyList<Local>> GetAllAsync();
        Task<Local> AddAsync(string nombre, string direccion);
        Task<bool> UpdateAsync(long id, string nombre, string direccion);
        Task<bool> DeleteAsync(long id);
    }
}
