namespace PeluPOS.Models.ApiDtos.Empleados;

public class EmpleadoDto
{
    public long idEmpleado { get; set; }
    public string nombre { get; set; } = "";
    public string? cargo { get; set; }
    public string? email { get; set; }
    public long? telefono { get; set; }
    public long? idLocal { get; set; }
    public string? LocalNombre { get; set; }
}
