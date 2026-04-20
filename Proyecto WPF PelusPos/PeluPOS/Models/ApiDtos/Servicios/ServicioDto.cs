using PeluPOS.Models.ApiDtos.Empleados;

namespace PeluPOS.Models.ApiDtos.Servicios
{
    public class ServicioDto
    {
        public long idServicio { get; set; }
        public string nombre { get; set; } = string.Empty;
        public decimal precio { get; set; }
        public string? descripcion { get; set; }
        public EmpleadoDto? idEmpleado { get; set; }
    }
}