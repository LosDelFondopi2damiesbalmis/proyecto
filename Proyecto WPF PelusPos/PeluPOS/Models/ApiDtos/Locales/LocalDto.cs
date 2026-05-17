namespace PeluPOS.Models.ApiDtos.Locales
{
    public class LocalDto
    {
        public long idLocal { get; set; }
        public string nombre { get; set; } = string.Empty;
        public string direccion { get; set; } = string.Empty;
    }
}