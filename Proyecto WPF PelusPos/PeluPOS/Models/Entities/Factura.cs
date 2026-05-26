using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class Factura
    {
        public long Id { get; set; }
        public decimal Monto { get; set; }
        public DateTime Fecha { get; set; }
        public bool Pendiente { get; set; }
        public string TipoPago { get; set; } = string.Empty;

        // Cliente asociado
        public long ClienteId { get; set; }
        public Cliente? Cliente { get; set; }

        // Empleado que genera la factura
        public long EmpleadoId { get; set; }
        public Empleado? Empleado { get; set; }

        // Líneas de la factura
        public ICollection<LineaFactura> Lineas { get; set; } = new List<LineaFactura>();
    }

}
