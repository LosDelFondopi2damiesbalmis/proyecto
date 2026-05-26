package com.proyecto.PeluPos.models
import com.google.gson.annotations.SerializedName
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
// Estas clases son solo para "leer" el JSON de la API
data class FacturaDto(
    val idFactura: Long,
    val monto: Double,
    val fecha: String, // Recibimos el String del JSON
    val pendiente: Boolean,
    val tipoPago: String,
    val idCliente: Cliente, // El JSON devuelve el objeto Cliente completo
    val idEmpleado: Empleado, // El JSON devuelve el objeto Empleado completo
    val facturaProductoCollection: List<FacturaProductoDto>,
    val facturaServicioCollection: List<FacturaServicioDto>
)

data class FacturaProductoDto(
    @SerializedName("producto")
    val producto: Producto,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("precioVendido")
    val precioVendido: Double? = null
)

// DTO para los Servicios
data class FacturaServicioDto(
    @SerializedName("servicio")
    val servicio: Servicio,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("precioCobrado")
    val precioCobrado: Double? = null
)
data class ClienteIdDto(val idCliente: Long)

// Clase envoltorio para el empleado
data class EmpleadoIdDto(val idEmpleado: Long)

// El DTO que enviaremos a la API en POST y PUT
data class FacturaRequestDto(
    val idFactura: Long? = null, // Nullable para el POST (donde el ID lo pone la BD)
    val monto: Double,
    val fecha: String,
    val pendiente: Boolean,
    val tipoPago: String,
    val idCliente: ClienteIdDto,
    val idEmpleado: EmpleadoIdDto
)