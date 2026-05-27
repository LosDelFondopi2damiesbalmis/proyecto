using System.Text.Json.Serialization;
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
    /// Not used for serialisation – kept for compatibility with any legacy code that
    /// still reads this field. Set [JsonIgnore] so it never conflicts with the nested
    /// "idLocal" object that the API actually sends/receives.
    /// </summary>
    [JsonIgnore]
    public long? idLocal { get; set; }

    /// <summary>
    /// Mapped to/from the JSON key "idLocal" because the Java backend serialises the
    /// ManyToOne relationship via getIdLocal() → property name "idLocal" with a full
    /// Local object as value, e.g.: "idLocal": { "idLocal": 1, "nombre": "Centro" }.
    /// </summary>
    [JsonPropertyName("idLocal")]
    public LocalDto? local { get; set; }

    /// <summary>Display-only local name, populated by the ViewModel layer.</summary>
    public string? LocalNombre { get; set; }
}
