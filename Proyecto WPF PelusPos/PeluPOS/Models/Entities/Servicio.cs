using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{

    public class Servicio
    {
        public long Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public decimal Precio { get; set; }
        public string Descripcion { get; set; } = string.Empty;

        public ICollection<LineaFactura> LineasFactura { get; set; } = new List<LineaFactura>();

        // Empleados que realizan este servicio (muchos-a-muchos)
        public ICollection<Empleado> Empleados { get; set; } = new List<Empleado>();
    }

}
