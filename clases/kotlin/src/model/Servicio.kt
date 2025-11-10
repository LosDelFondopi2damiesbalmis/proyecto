data class Servicio(
    val idServicio: Long,
    var nombre: String,
    var precio: Double,
    var descripcion: String,
    val empleado: Empleado
)