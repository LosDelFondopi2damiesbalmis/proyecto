using System.Collections.Generic;

namespace Model
{
    public class Local
    {
        public long IdLocal { get; set; }
        public string Nombre { get; set; }
        public string Direccion { get; set; }
        public List<Empleado> Empleados { get; set; }
    }
}
