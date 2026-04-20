using PeluPOS.Models.ApiDtos.Servicios;

namespace PeluPOS.Models.ApiDtos.Facturas
{
    public class FacturaServicioDto
    {
        public FacturaServicioPkDto? facturaServicioPK { get; set; }
        public int? cantidad { get; set; }
        public decimal? precioCobrado { get; set; }
        public ServicioDto? servicio { get; set; }
    }
}