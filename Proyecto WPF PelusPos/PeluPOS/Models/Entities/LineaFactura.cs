using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class LineaFactura
    {
        public long Id { get; set; }

        public long FacturaId { get; set; }
        public Factura? Factura { get; set; }

        public long? ProductoId { get; set; }
        public Producto? Producto { get; set; }

        public long? ServicioId { get; set; }
        public Servicio? Servicio { get; set; }

        public int Cantidad { get; set; }
        public decimal PrecioUnitario { get; set; }
        public decimal ImporteTotal => Cantidad * PrecioUnitario;
    }
}
