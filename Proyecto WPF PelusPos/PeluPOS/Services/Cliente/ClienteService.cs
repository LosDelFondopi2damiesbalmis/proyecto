using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class ClienteService : IClienteService
    {
        public Task<IEnumerable<Cliente>> GetClientesAsync()
        {
            // TODO: Sustituye estos datos de ejemplo por tu MockData o tu BD real.

            var clientes = new List<Cliente>
            {
                new Cliente { Id = 1, Nombre = "Ana Martínez", Telefono = 600111222, Deuda = 0m },
                new Cliente { Id = 2, Nombre = "Luis Gómez", Telefono = 600333444, Deuda = 15.50m },
                new Cliente { Id = 3, Nombre = "Carmen Ruiz", Telefono = 600555666, Deuda = 0m },
                new Cliente { Id = 4, Nombre = "Marcos Pérez", Telefono = 600777888, Deuda = 8.00m },
            };

            return Task.FromResult<IEnumerable<Cliente>>(clientes);
        }
    }
}

