using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.ViewModels.TPV
{
    public class TpvItemCard
    {
        public Producto? Producto { get; init; }
        public Servicio? Servicio { get; init; }

        public string Nombre => Producto?.Nombre ?? Servicio?.Nombre ?? "";
        public decimal Precio => (Producto?.PrecioVenta) ?? (Servicio?.Precio) ?? 0m;

        public bool EsProducto => Producto != null;
        public long Stock => Producto?.Stock ?? 0;

        public bool Disponible => !EsProducto || Stock > 0;
    }
}
