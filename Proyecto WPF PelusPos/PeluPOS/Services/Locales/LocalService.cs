using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Locales
{
    public class LocalService : ILocalService
    {
        private List<Local> Locales = MockData.Locales;
        public Task<IReadOnlyList<Local>> GetAllAsync() => Task.FromResult((IReadOnlyList<Local>)Locales);

        public Task<Local> AddAsync(string nombre, string direccion)
        {
            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre del local es obligatorio.", nameof(nombre));

            var newId = MockData.Locales.Any() ? MockData.Locales.Max(l => l.Id) + 1 : 1;

            var local = new Local
            {
                Id = newId,
                Nombre = nombre.Trim(),
                Direccion = (direccion ?? string.Empty).Trim(),
                Empleados = [] // relación inicial vacía
            };

            MockData.Locales.Add(local);

            return Task.FromResult(local);
        }
    }
}
