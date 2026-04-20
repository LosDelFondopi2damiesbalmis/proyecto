using PeluPOS.Models.ApiDtos.Productos;

namespace PeluPOS.Models.ApiDtos.Facturas
{
    public class FacturaProductoDto
    {
        public FacturaProductoPkDto? facturaProductoPK { get; set; }
        public int? cantidad { get; set; }
        public decimal? precioVendido { get; set; }
        public ProductoDto? producto { get; set; }
    }
}