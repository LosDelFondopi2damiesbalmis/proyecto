using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class Empleado
    {
        public long Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public long Telefono { get; set; }
        public string Email { get; set; } = string.Empty;
        public string Cargo { get; set; } = string.Empty;

        // Relación con Local
        public long LocalId { get; set; }
        public Local Local { get; set; }

        // Relación composición con Usuario
        public Usuario? Usuario { get; set; }

        // Servicios que puede realizar este empleado
        public ICollection<Servicio> Servicios { get; set; } = new List<Servicio>();

        // Facturas generadas por este empleado
        public ICollection<Factura> Facturas { get; set; } = new List<Factura>();
    }

}
