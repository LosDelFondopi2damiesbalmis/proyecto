namespace Model
{
    public class Empleado
    {
        public long IdEmpleado { get; set; }
        public long Telefono { get; set; }
        public string Email { get; set; }
        public string Cargo { get; set; }
        public string Nombre { get; set; }
        public Local Local { get; set; }
    }
}
