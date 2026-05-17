namespace PeluPOS.Models.ApiDtos.Productos
{
    public class ProductoDto
    {
        public long idProducto { get; set; }
        public string nombre { get; set; } = string.Empty;
        public decimal precioCompra { get; set; }
        public decimal precioVenta { get; set; }
        public int stock { get; set; }
    }
}