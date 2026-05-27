using System;
using System.Collections.Generic;
using System.Linq;
using PeluPOS.Models.Entities;

namespace PeluPOS.ViewModels.VentaPage
{
    public class VentaRowViewModel
    {
        public Factura Factura { get; set; } = default!;

        public long     Id            => Factura.Id;
        public DateTime Fecha         => Factura.Fecha;
        public bool     Pendiente     => Factura.Pendiente;
        public string   TipoPago      => Factura.TipoPago;
        public decimal  Monto         => Factura.Monto;
        public int      NumItems      => Factura.Lineas.Count;

        public string ClienteNombre  => Factura.Cliente?.Nombre  ?? $"Cliente #{Factura.ClienteId}";
        public string EmpleadoNombre => Factura.Empleado?.Nombre ?? $"Empleado #{Factura.EmpleadoId}";
        public string EstadoPago     => Factura.Pendiente ? "⏳ Pendiente" : "✔ Pagado";

        // ── Productos ──────────────────────────────────────────────────────────
        public string ProductosResumen
        {
            get
            {
                var nombres = Factura.Lineas
                    .Where(l => l.Producto is not null)
                    .Select(l => l.Producto!.Nombre)
                    .Where(n => !string.IsNullOrWhiteSpace(n))
                    .Distinct()
                    .ToList();
                return nombres.Count > 0 ? string.Join(", ", nombres) : string.Empty;
            }
        }

        // ── Servicios ──────────────────────────────────────────────────────────
        public string ServiciosResumen
        {
            get
            {
                var nombres = Factura.Lineas
                    .Where(l => l.Servicio is not null)
                    .Select(l => l.Servicio!.Nombre)
                    .Where(n => !string.IsNullOrWhiteSpace(n))
                    .Distinct()
                    .ToList();
                return nombres.Count > 0 ? string.Join(", ", nombres) : string.Empty;
            }
        }

        public bool TieneProductos => !string.IsNullOrEmpty(ProductosResumen);
        public bool TieneServicios => !string.IsNullOrEmpty(ServiciosResumen);
        public bool SinItems       => !TieneProductos && !TieneServicios;

        // ── Tooltip / resumen completo ─────────────────────────────────────────
        public string ItemsResumen
        {
            get
            {
                var partes = new List<string>();
                if (TieneProductos) partes.Add($"📦 {ProductosResumen}");
                if (TieneServicios) partes.Add($"✂ {ServiciosResumen}");
                return partes.Count > 0 ? string.Join("  |  ", partes) : "(Sin items)";
            }
        }
    }
}
