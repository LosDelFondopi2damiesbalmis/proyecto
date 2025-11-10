namespace Model
{
    public class Usuario
    {
        public long IdUsuario { get; set; }
        public string NombreUsuario { get; set; }
        public string Contrasena { get; set; }
        public Empleado Empleado { get; set; }
    }
}
