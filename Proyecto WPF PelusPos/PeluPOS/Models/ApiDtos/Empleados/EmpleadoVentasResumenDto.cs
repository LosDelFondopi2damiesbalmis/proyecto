namespace PeluPOS.Models.ApiDtos.Empleados
{
    public class EmpleadoVentasResumenDto
    {
        public double totalFacturado { get; set; }
        public long productosVendidos { get; set; }
        public long serviciosRealizados { get; set; }
    }
}