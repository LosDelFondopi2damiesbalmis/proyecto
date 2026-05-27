using PeluPOS.Services.Api;
using PeluPOS.Services.Auth;
using PeluPOS.Services.Locales;
using PeluPOS.Services.Productos;
using PeluPOS.Services.Servicios;
using PeluPOS.Services.TPV;
using PeluPOS.Services.Ventas;

namespace PeluPOS.Services
{
    /// <summary>
    /// Contenedor estático de servicios. Cambia <see cref="ApiBaseUrl"/> para apuntar a otra instancia de la API.
    /// </summary>
    public static class AppServices
    {
        public const string ApiBaseUrl = "http://pelupos.spaincentral.cloudapp.azure.com:8080/pelupos/servicio/";

        // ── cliente HTTP compartido (el token JWT se actualiza tras el login) ──
        public static readonly ApiClient ApiClient = new(ApiBaseUrl);

        // ── servicios de acceso a la API ──
        public static readonly AuthApiService AuthApi = new(ApiClient);
        public static readonly UsuarioApiService UsuarioApi = new(ApiClient);
        public static readonly ClienteApiService ClienteApi = new(ApiClient);
        public static readonly EmpleadoApiService EmpleadoApi = new(ApiClient);
        public static readonly FacturaApiService FacturaApi = new(ApiClient);
        public static readonly LocalApiService LocalApi = new(ApiClient);
        public static readonly ProductoApiService ProductoApi = new(ApiClient);
        public static readonly ServicioApiService ServicioApi = new(ApiClient);

        // ── servicios de negocio ──
        public static readonly IAuthService Auth = new AuthService(UsuarioApi, AuthApi, EmpleadoApi);
        public static readonly IClienteService Clientes = new ClienteService(ClienteApi);
        public static readonly IEmpleadoService Empleados = new EmpleadoService(EmpleadoApi, LocalApi, FacturaApi);
        public static readonly IEmpleadoStatsService EmpleadoStats = new EmpleadosStatsService(EmpleadoApi, FacturaApi);
        public static readonly ILocalService Locales = new LocalService(LocalApi);
        public static readonly IProductoService Productos = new ProductoService(ProductoApi, FacturaApi);
        public static readonly IServicioService Servicios = new ServicioService(ServicioApi, FacturaApi);
        public static readonly IVentaService Ventas = new VentaService(FacturaApi, ProductoApi, ServicioApi);
        public static readonly ICatalogService Catalog = new CatalogService(ProductoApi, ServicioApi, ClienteApi);
        public static readonly ITpvVentaService TpvVenta = new TpvVentaService(FacturaApi);
    }
}
