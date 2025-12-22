using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class ClienteService : IClienteService
    {
        public List<Cliente> Clientes = MockData.Clientes;
        public Task<IReadOnlyList<Cliente>> GetAllAsync() => Task.FromResult((IReadOnlyList<Cliente>)Clientes);

    }
}

