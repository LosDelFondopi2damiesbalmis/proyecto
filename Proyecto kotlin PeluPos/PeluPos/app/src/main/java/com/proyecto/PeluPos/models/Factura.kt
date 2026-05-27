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

data class FacturaMinDto(
    @SerializedName("idFactura")
    val idFactura: Long
)


// 1. Creamos los DTOs para las Claves Compuestas
data class FacturaProductoPKDto(
    @SerializedName("idFactura") val idFactura: Long,

    @SerializedName("idProducto") val idProducto: Long
)

data class FacturaServicioPKDto(
    @SerializedName("idFactura") val idFactura: Long,

    @SerializedName("idServicio") val idServicio: Long
)

// 2. Actualizamos los DTOs principales
data class FacturaProductoDto(
    @SerializedName("facturaProductoPK") val facturaProductoPK: FacturaProductoPKDto, // 🚀 Mandamos la clave directamente
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("producto") val producto: Producto? = null,
    @SerializedName("precioVendido") val precioVendido: Double? = null
)

data class FacturaServicioDto(
    @SerializedName("facturaServicioPK") val facturaServicioPK: FacturaServicioPKDto, // 🚀 Mandamos la clave directamente
    @SerializedName("cantidad") val cantidad: Int,
    @SerializedName("servicio") val servicio: Servicio? = null,
    @SerializedName("precioCobrado") val precioCobrado: Double? = null

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
