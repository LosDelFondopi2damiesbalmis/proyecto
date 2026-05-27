using PeluPOS.Models.ApiDtos.Locales;

namespace PeluPOS.Models.ApiDtos.Empleados;

public class EmpleadoDto
{
    public long idEmpleado { get; set; }
    public string nombre { get; set; } = "";
    public string? cargo { get; set; }
    public string? email { get; set; }
    public long? telefono { get; set; }

    /// <summary>
    /// Flat scalar id – populated when the API returns idLocal directly as a number.
    /// </summary>
    public long? idLocal { get; set; }

    /// <summary>
    /// Nested local object – populated when the API serialises the relationship as a
    /// full object (e.g. Spring Boot default: "local": { "idLocal": 1, "nombre": "…" }).
    /// Takes priority over idLocal.
    /// </summary>
    public LocalDto? local { get; set; }

    /// <summary>Display-only local name, populated by the ViewModel layer.</summary>
    public string? LocalNombre { get; set; }
}
