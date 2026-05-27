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

        public async Task<bool> CreateAsync(ClienteDto dto)
        {
            var result = await _api.CreateAsync(dto);
            return result != null;
        }

        public async Task<bool> UpdateAsync(ClienteDto dto)
        {
            var result = await _api.UpdateAsync(dto);
            return result != null;
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var result = await _api.DeleteAsync(id);
            return result != null;
        }
    }
}
