using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.ViewModels.VentaPage
{
    public class VentaRowViewModel
    {
        public Factura Factura { get; set; } = default!;

        public long Id => Factura.Id;
        public DateTime Fecha => Factura.Fecha;
        public bool Pendiente => Factura.Pendiente;
        public string TipoPago => Factura.TipoPago;
        public decimal Monto => Factura.Monto;

        public string ItemsResumen
        {
            get
            {
                var nombres = Factura.Lineas
                    .Select(l => l.Producto?.Nombre ?? l.Servicio?.Nombre)
                    .Where(n => !string.IsNullOrWhiteSpace(n))
                    .Distinct()
                    .ToList();

                if (nombres.Count == 0) return "(Sin items)";
                return string.Join(" + ", nombres);
            }
        }
    }
}
