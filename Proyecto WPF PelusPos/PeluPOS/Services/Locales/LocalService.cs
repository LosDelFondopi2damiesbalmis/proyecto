using PeluPOS.Models.ApiDtos.Locales;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.Locales
{
    public class LocalService : ILocalService
    {
        private readonly ILocalApiService _api;

        public LocalService(ILocalApiService api)
        {
            _api = api;
        }

        public async Task<IReadOnlyList<Local>> GetAllAsync()
        {
            var dtos = await _api.GetAllAsync();
            return dtos.Select(d => ToEntity(d)).ToList();
        }

        public async Task<Local> AddAsync(string nombre, string direccion)
        {
            var dto = new LocalDto { nombre = nombre, direccion = direccion };
            await _api.CreateAsync(dto);

            // Devolvemos el último creado recargando la lista
            var all = await _api.GetAllAsync();
            return ToEntity(all.Last());
        }

        private static Local ToEntity(LocalDto d) => new Local
        {
            Id = d.idLocal,
            Nombre = d.nombre ?? "",
            Direccion = d.direccion ?? ""
        };
    }
}
