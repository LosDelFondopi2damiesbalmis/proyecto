using PeluPOS.Models.ApiDtos.Clientes;
using PeluPOS.Models.ApiDtos.Empleados;

namespace PeluPOS.Models.ApiDtos.Facturas
{
    public class FacturaDto
    {
        public long idFactura { get; set; }
        public decimal monto { get; set; }
        public DateTime fecha { get; set; }
        public bool? pendiente { get; set; }
        public string? tipoPago { get; set; }

        public ClienteDto? idCliente { get; set; }
        public EmpleadoDto? idEmpleado { get; set; }

        public List<FacturaProductoDto> facturaProductoCollection { get; set; } = new();
        public List<FacturaServicioDto> facturaServicioCollection { get; set; } = new();
    }
}