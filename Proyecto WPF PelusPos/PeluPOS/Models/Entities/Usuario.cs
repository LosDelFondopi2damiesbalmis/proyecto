using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class Usuario
    {
        public long Id { get; set; }
        public string Contrasena { get; set; } = string.Empty;

        // Opcionalmente, navegación inversa
        public long EmpleadoId { get; set; }
        public Empleado Empleado { get; set; }
    }

}
