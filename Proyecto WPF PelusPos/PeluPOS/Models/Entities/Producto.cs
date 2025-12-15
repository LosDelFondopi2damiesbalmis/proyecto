using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class Producto
    {
        public long Id { get; set; }
        public string Nombre { get; set; } = string.Empty;

        public decimal PrecioCompra { get; set; }
        public decimal PrecioVenta { get; set; }

        public long Stock { get; set; }

        public ICollection<LineaFactura> LineasFactura { get; set; } = new List<LineaFactura>();
    }

}
