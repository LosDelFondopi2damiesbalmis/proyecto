using System.Collections.Generic;
using System.Threading.Tasks;
using PeluPOS.Models.ApiDtos.Empleados;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services
{
    public class EmpleadoService : IEmpleadoService
    {
        private readonly IEmpleadoApiService _empleadoApi;
        private readonly ILocalApiService    _localApi;
        private readonly IFacturaApiService  _facturaApi;

        public EmpleadoService(
            IEmpleadoApiService empleadoApi,
            ILocalApiService    localApi,
            IFacturaApiService  facturaApi)
        {
            _empleadoApi = empleadoApi;
            _localApi    = localApi;
            _facturaApi  = facturaApi;
        }

        // ── mappers ──────────────────────────────────────────────────────────

        private static Empleado MapEmpleado(EmpleadoDto dto)
        {
            // The backend may send the local either as a nested object
            // (Spring Boot default: "local": { "idLocal": 1, "nombre": "…" })
            // or as a flat scalar ("idLocal": 1).  Try the nested object first.
            var localId     = dto.local?.idLocal ?? dto.idLocal ?? 0L;
            var localNombre = dto.local?.nombre  ?? string.Empty;

            return new Empleado
            {
                Id       = dto.idEmpleado,
                Nombre   = dto.nombre,
                Cargo    = dto.cargo    ?? string.Empty,
                Email    = dto.email    ?? string.Empty,
                Telefono = dto.telefono ?? 0L,
                LocalId  = localId,
                Local    = localId == 0 ? null : new Local
                {
                    Id     = localId,
                    Nombre = localNombre
                }
            };
        }

        private static Factura MapFactura(Models.ApiDtos.Facturas.FacturaDto dto) => new Factura
        {
            Id         = dto.idFactura,
            Monto      = dto.monto,
            Fecha      = dto.fecha,
            Pendiente  = dto.pendiente  ?? false,
            TipoPago   = dto.tipoPago   ?? string.Empty,
            EmpleadoId = dto.idEmpleado?.idEmpleado ?? 0L,
            ClienteId  = dto.idCliente?.idCliente   ?? 0L
        };

        // ── IEmpleadoService ─────────────────────────────────────────────────

        public async Task<IReadOnlyList<Empleado>> GetAllAsync()
        {
            var dtos = await _empleadoApi.GetAllAsync();
            return dtos.Select(MapEmpleado).ToList();
        }

        public async Task<Empleado?> GetByIdAsync(long empleadoId)
        {
            var all = await _empleadoApi.GetAllAsync();
            var dto = all.FirstOrDefault(e => e.idEmpleado == empleadoId);
            return dto is null ? null : MapEmpleado(dto);
        }

        public async Task<IReadOnlyList<Local>> GetLocalesAsync()
        {
            var dtos = await _localApi.GetAllAsync();
            return dtos.Select(d => new Local
            {
                Id     = d.idLocal,
                Nombre = d.nombre
            }).ToList();
        }

        public async Task<Empleado> CreateAsync(
            string nombre,
            long   telefono,
            string email,
            string cargo,
            long   localId)
        {
            var dto = new EmpleadoDto
            {
                nombre   = nombre,
                cargo    = cargo,
                email    = email,
                telefono = telefono,
                idLocal  = localId
            };
            await _empleadoApi.CreateAsync(dto);

            // Reload to get the newly assigned id
            var all = await _empleadoApi.GetAllAsync();
            var created = all
                .Where(e => e.nombre == nombre && e.email == email)
                .OrderByDescending(e => e.idEmpleado)
                .FirstOrDefault();

            return created is not null ? MapEmpleado(created) : MapEmpleado(dto);
        }

        public async Task UpdateAsync(
            long   empleadoId,
            string nombre,
            long   telefono,
            string email,
            string cargo,
            long   localId)
        {
            var dto = new EmpleadoDto
            {
                idEmpleado = empleadoId,
                nombre     = nombre,
                cargo      = cargo,
                email      = email,
                telefono   = telefono,
                idLocal    = localId          // ← was missing; local assignment never saved
            };
            await _empleadoApi.UpdateAsync(dto);
        }

        public async Task DeleteAsync(long empleadoId)
        {
            await _empleadoApi.DeleteAsync(empleadoId);
        }

        public async Task<IReadOnlyList<Factura>> GetFacturasByEmpleadoAsync(long empleadoId)
        {
            var all = await _facturaApi.GetAllAsync();
            return all
                .Where(f => f.idEmpleado?.idEmpleado == empleadoId)
                .Select(MapFactura)
                .ToList();
        }
    }
}
