using System.Collections.Generic;
using System.Threading.Tasks;
using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services
{
    public class ClienteService : IClienteService
    {
        private readonly IClienteApiService _api;

        public ClienteService(IClienteApiService api)
        {
            _api = api;
        }

        private static Cliente Map(ClienteDto dto) => new Cliente
        {
            Id       = dto.idCliente,
            Nombre   = dto.nombre,
            Deuda    = dto.deuda ?? 0m,
            Telefono = dto.telefono ?? 0L
        };

        public async Task<IReadOnlyList<Cliente>> GetAllAsync()
        {
            var dtos = await _api.GetAllAsync();
            return dtos.Select(Map).ToList();
        }
    }
}
