using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public class EmpleadoService : IEmpleadoService
    {

        public Task<IReadOnlyList<Empleado>> GetAllAsync()
        {
            return Task.FromResult(
                (IReadOnlyList<Empleado>)MockData.Empleados.ToList()
            );
        }

        public Task<Empleado?> GetByIdAsync(long empleadoId)
        {
            var empleado = MockData.Empleados
                .FirstOrDefault(e => e.Id == empleadoId);

            return Task.FromResult(empleado);
        }

        public Task<IReadOnlyList<Local>> GetLocalesAsync()
        {
            return Task.FromResult(
                (IReadOnlyList<Local>)MockData.Locales.ToList()
            );
        }

        public Task<IReadOnlyList<Factura>> GetFacturasByEmpleadoAsync(long empleadoId)
        {
            var facturas = MockData.Facturas
                .Where(f => f.Empleado?.Id == empleadoId)
                .OrderByDescending(f => f.Fecha)
                .ToList();

            return Task.FromResult(
                (IReadOnlyList<Factura>)facturas
            );
        }


        public Task<Empleado> CreateAsync(
            string nombre,
            long telefono,
            string email,
            string cargo,
            long localId,
            string password)
        {
            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre es obligatorio.");

            if (string.IsNullOrWhiteSpace(email))
                throw new ArgumentException("El email es obligatorio.");

            if (string.IsNullOrWhiteSpace(password))
                throw new ArgumentException("La contraseña es obligatoria.");

            var local = MockData.Locales
                .FirstOrDefault(l => l.Id == localId)
                ?? throw new InvalidOperationException("Local no encontrado.");

            var newId = MockData.Empleados.Any()
                ? MockData.Empleados.Max(e => e.Id) + 1
                : 1;

            var empleado = new Empleado
            {
                Id = newId,
                Nombre = nombre.Trim(),
                Telefono = telefono,
                Email = email.Trim(),
                Cargo = cargo?.Trim() ?? string.Empty,

                LocalId = local.Id,
                Local = local,

                Usuario = new Usuario
                {
                    Id = newId,
                    EmpleadoId = newId,
                    Contrasena = password
                },

                Facturas = new List<Factura>(),
                Servicios = new List<Servicio>()
            };

            MockData.Empleados.Add(empleado);
            local.Empleados.Add(empleado);

            return Task.FromResult(empleado);
        }

        public Task UpdateAsync(
            long empleadoId,
            string nombre,
            long telefono,
            string email,
            string cargo,
            long localId)
        {
            var empleado = MockData.Empleados
                .FirstOrDefault(e => e.Id == empleadoId)
                ?? throw new InvalidOperationException("Empleado no encontrado.");

            if (string.IsNullOrWhiteSpace(nombre))
                throw new ArgumentException("El nombre es obligatorio.");

            if (string.IsNullOrWhiteSpace(email))
                throw new ArgumentException("El email es obligatorio.");

            var nuevoLocal = MockData.Locales
                .FirstOrDefault(l => l.Id == localId)
                ?? throw new InvalidOperationException("Local no encontrado.");

            if (empleado.Local != null && empleado.Local.Id != nuevoLocal.Id)
            {
                empleado.Local.Empleados.Remove(empleado);
            }

            empleado.Nombre = nombre.Trim();
            empleado.Telefono = telefono;
            empleado.Email = email.Trim();
            empleado.Cargo = cargo?.Trim() ?? string.Empty;

            empleado.LocalId = nuevoLocal.Id;
            empleado.Local = nuevoLocal;

            if (!nuevoLocal.Empleados.Contains(empleado))
                nuevoLocal.Empleados.Add(empleado);

            return Task.CompletedTask;
        }

        public Task DeleteAsync(long empleadoId)
        {
            var empleado = MockData.Empleados
                .FirstOrDefault(e => e.Id == empleadoId);

            if (empleado == null)
                return Task.CompletedTask;

            // Quitarlo del local
            empleado.Local?.Empleados.Remove(empleado);

            // Nota: NO borramos facturas (histórico)
            MockData.Empleados.Remove(empleado);

            return Task.CompletedTask;
        }
    }
}
