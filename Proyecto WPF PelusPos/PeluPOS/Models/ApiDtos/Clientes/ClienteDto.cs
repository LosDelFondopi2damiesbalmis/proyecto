namespace PeluPOS.Models.ApiDtos.Clientes
{
    public class ClienteDto
    {
        public long idCliente { get; set; }
        public string nombre { get; set; } = string.Empty;
        public decimal? deuda { get; set; }
        public long? telefono { get; set; }
    }
}