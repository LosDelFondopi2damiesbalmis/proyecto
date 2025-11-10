namespace Model
{
    public class Producto
    {
        public long IdProducto { get; set; }
        public string Nombre { get; set; }
        public double PrecioCompra { get; set; }
        public double PrecioVenta { get; set; }
        public int Stock { get; set; }
    }
}
