import java.util.Date

data class Factura(
    val idFactura: Long,
    var monto: Double = 0.0,
    val fecha: Date = Date(),
    var pendiente: Boolean = true,
    var tipoPago: String = "",
    val cliente: Cliente,
    val empleado: Empleado,
    val productos: MutableList<Producto> = mutableListOf(),
    val servicios: MutableList<Servicio> = mutableListOf()
)