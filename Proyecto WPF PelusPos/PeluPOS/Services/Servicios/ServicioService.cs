using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services.Servicios
{
    public class ServicioService : IServicioService
    {
        public Task<IReadOnlyList<Servicio>> GetAllAsync() => Task.FromResult((IReadOnlyList<Servicio>)MockData.Servicios);

        public Task<Servicio> AddAsync(string nombre, decimal precio, string descripcion)
        {
            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre es obligatorio.", nameof(nombre));
            if (precio < 0)
                throw new ArgumentException("El precio no puede ser negativo.", nameof(precio));

            var newId = MockData.Servicios.Any() ? MockData.Servicios.Max(s => s.Id) + 1 : 1;

            var servicio = new Servicio
            {
                Id = newId,
                Nombre = nombre.Trim(),
                Precio = precio,
                Descripcion = (descripcion ?? string.Empty).Trim()
            };

            MockData.Servicios.Add(servicio);
            return Task.FromResult(servicio);
        }

        public Task UpdateAsync(long id, string nombre, decimal precio, string descripcion)
        {
            var s = MockData.Servicios.FirstOrDefault(x => x.Id == id)
                    ?? throw new InvalidOperationException("Servicio no encontrado.");

            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre es obligatorio.", nameof(nombre));
            if (precio < 0)
                throw new ArgumentException("El precio no puede ser negativo.", nameof(precio));

            s.Nombre = nombre.Trim();
            s.Precio = precio;
            s.Descripcion = (descripcion ?? string.Empty).Trim();

            return Task.CompletedTask;
        }

        public Task DeleteAsync(long id)
        {
            var s = MockData.Servicios.FirstOrDefault(x => x.Id == id);
            if (s != null) MockData.Servicios.Remove(s);
            return Task.CompletedTask;
        }

        public Task<IReadOnlyList<Factura>> GetFacturasByServicioAsync(long servicioId)
        {
            var facturas = MockData.Facturas
                .Where(f => f.Lineas.Any(l => l.Servicio?.Id == servicioId))
                .OrderByDescending(f => f.Fecha)
                .ToList();

            return Task.FromResult((IReadOnlyList<Factura>)facturas);
        }
        public Task<IReadOnlyList<Servicio>> GetServiciosRelacionadosAsync(long servicioId)
        {
            // Facturas donde aparece este servicio
            var facturas = MockData.Facturas
                .Where(f => f.Lineas.Any(l => l.ServicioId == servicioId))
                .ToList();

            // Todos los ServicioId de esas facturas (distintos), excluyendo el servicio actual
            var ids = facturas
                .SelectMany(f => f.Lineas)
                .Where(l => l.ServicioId.HasValue)
                .Select(l => l.ServicioId!.Value)
                .Distinct()
                .Where(id => id != servicioId)
                .ToHashSet();

            var relacionados = MockData.Servicios
                .Where(s => ids.Contains(s.Id))
                .OrderBy(s => s.Nombre)
                .ToList();

            return Task.FromResult((IReadOnlyList<Servicio>)relacionados);
        }
    }
}
