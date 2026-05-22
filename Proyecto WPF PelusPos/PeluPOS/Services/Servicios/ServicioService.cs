using PeluPOS.Models.ApiDtos.Servicios;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.Servicios
{
    public class ServicioService : IServicioService
    {
        private readonly IServicioApiService _servicioApi;
        private readonly IFacturaApiService  _facturaApi;

        public ServicioService(
            IServicioApiService servicioApi,
            IFacturaApiService  facturaApi)
        {
            _servicioApi = servicioApi;
            _facturaApi  = facturaApi;
        }

        // ── helpers ────────────────────────────────────────────────────────
        private static Servicio ToEntity(ServicioDto d) => new Servicio
        {
            Id          = d.idServicio,
            Nombre      = d.nombre,
            Precio      = d.precio,
            Descripcion = d.descripcion ?? string.Empty
        };

        // ── CRUD ───────────────────────────────────────────────────────────
        public async Task<IReadOnlyList<Servicio>> GetAllAsync()
        {
            var dtos = await _servicioApi.GetAllAsync();
            return dtos.Select(ToEntity).ToList();
        }

        public async Task<Servicio> AddAsync(string nombre, decimal precio, string descripcion)
        {
            var dto = new ServicioDto
            {
                nombre      = nombre,
                precio      = precio,
                descripcion = descripcion
            };
            await _servicioApi.CreateAsync(dto);
            var all = await _servicioApi.GetAllAsync();
            var created = all.Last(s => s.nombre == nombre);
            return ToEntity(created);
        }

        public async Task UpdateAsync(long id, string nombre, decimal precio, string descripcion)
        {
            var dto = new ServicioDto
            {
                idServicio  = id,
                nombre      = nombre,
                precio      = precio,
                descripcion = descripcion
            };
            await _servicioApi.UpdateAsync(dto);
        }

        public async Task DeleteAsync(long id)
        {
            await _servicioApi.DeleteAsync(id);
        }

        // ── queries ────────────────────────────────────────────────────────
        public async Task<IReadOnlyList<Factura>> GetFacturasByServicioAsync(long servicioId)
        {
            var facturas = await _facturaApi.GetAllAsync();
            return facturas
                .Where(f => f.facturaServicioCollection?
                    .Any(fs => fs.facturaServicioPK?.idServicio == servicioId) == true)
                .Select(f => new Factura
                {
                    Id         = f.idFactura,
                    Monto      = f.monto,
                    Fecha      = f.fecha,
                    Pendiente  = f.pendiente  ?? false,
                    TipoPago   = f.tipoPago   ?? string.Empty,
                    EmpleadoId = f.idEmpleado?.idEmpleado ?? 0L,
                    ClienteId  = f.idCliente?.idCliente   ?? 0L
                })
                .ToList();
        }

        public async Task<IReadOnlyList<Servicio>> GetServiciosRelacionadosAsync(long servicioId)
        {
            var facturas = await _facturaApi.GetAllAsync();
            var relatedIds = facturas
                .Where(f => f.facturaServicioCollection?
                    .Any(fs => fs.facturaServicioPK?.idServicio == servicioId) == true)
                .SelectMany(f => f.facturaServicioCollection!
                    .Select(fs => fs.facturaServicioPK!.idServicio))
                .Where(id => id != servicioId)
                .Distinct()
                .ToHashSet();

            var all = await _servicioApi.GetAllAsync();
            return all.Where(s => relatedIds.Contains(s.idServicio)).Select(ToEntity).ToList();
        }
    }
}
