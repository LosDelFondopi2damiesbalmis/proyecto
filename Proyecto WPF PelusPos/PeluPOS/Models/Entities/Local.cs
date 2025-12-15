using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class Local
    {
        public long Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public string Direccion { get; set; } = string.Empty;

        public ICollection<Empleado> Empleados { get; set; } = new List<Empleado>();
    }

}
