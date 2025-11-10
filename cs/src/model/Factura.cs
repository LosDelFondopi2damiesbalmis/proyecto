using System;
using System.Collections.Generic;

namespace Model
{
    public class Factura
    {
        public long IdFactura { get; set; }
        public double Monto { get; set; }
        public DateTime Fecha { get; set; }
        public bool Pendiente { get; set; }
        public string TipoPago { get; set; }
        public Cliente Cliente { get; set; }
        public Empleado Empleado { get; set; }
        public List<Producto> Productos { get; set; }
        public List<Servicio> Servicios { get; set; }
    }
}
