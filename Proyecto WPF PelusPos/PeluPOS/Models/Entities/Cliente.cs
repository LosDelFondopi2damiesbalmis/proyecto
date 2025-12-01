using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Models.Entities
{
    public class Cliente
    {
        public long Id { get; set; }
        public string Nombre { get; set; } = string.Empty;
        public long Telefono { get; set; }

        // Mejor decimal para dinero
        public decimal Deuda { get; set; }

        public ICollection<Factura> Facturas { get; set; } = new List<Factura>();
    }

}
