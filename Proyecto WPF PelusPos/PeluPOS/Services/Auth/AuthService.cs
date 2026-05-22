using PeluPOS.Models.ApiDtos.Auth;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Api;

namespace PeluPOS.Services.Auth
{
    public class AuthService : IAuthService
    {
        private readonly UsuarioApiService  _usuarioApi;
        private readonly AuthApiService     _authApi;
        private readonly EmpleadoApiService _empleadoApi;

        public AuthService(UsuarioApiService usuarioApi, AuthApiService authApi, EmpleadoApiService empleadoApi)
        {
            _usuarioApi  = usuarioApi;
            _authApi     = authApi;
            _empleadoApi = empleadoApi;
        }

        public async Task<IReadOnlyList<Usuario>> GetUsersAsync()
        {
            var dtos = await _usuarioApi.GetUsuariosAsync();
            return dtos.Select(d => new Usuario
            {
                Id         = d.idUsuario,
                Contrasena = d.contrasena,
                Roles      = RoleMapper.Parse(d.rolUsuario ?? "EMPLEADO"),
                EmpleadoId = d.idEmpleado?.idEmpleado ?? 0L
            }).ToList();
        }

        public async Task<Empleado?> ResolveEmpleadoAsync(Usuario usuario)
        {
            if (usuario.EmpleadoId == 0) return null;

            var empleados = await _empleadoApi.GetAllAsync();
            var dto = empleados.FirstOrDefault(e => e.idEmpleado == usuario.EmpleadoId);
            if (dto is null) return null;

            return new Empleado
            {
                Id       = dto.idEmpleado,
                Nombre   = dto.nombre,
                Cargo    = dto.cargo    ?? string.Empty,
                Email    = dto.email    ?? string.Empty,
                Telefono = dto.telefono ?? 0L
            };
        }

        public async Task<bool> ValidatePasswordAsync(Usuario usuario, string password)
        {
            var dtos = await _usuarioApi.GetUsuariosAsync();
            var dto  = dtos.FirstOrDefault(d => d.idUsuario == usuario.Id);
            if (dto is null) return false;

            var request = new LoginRequestDto
            {
                usuario    = dto.usuario,
                contrasena = password
            };

            var response = await _authApi.LoginAsync(request);
            return !string.IsNullOrWhiteSpace(response?.jwtToken);
        }
    }
}
