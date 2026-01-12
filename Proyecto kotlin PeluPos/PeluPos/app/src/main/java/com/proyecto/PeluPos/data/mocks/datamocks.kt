package com.proyecto.PeluPos.data.mocks

import com.proyecto.PeluPos.models.RolUsuario

data class ClienteMock(
    val idCliente: Long = 0L,
    val nombre: String = "",
    val deuda: Double = 0.0,
    val telefono: Long = 0L
)

data class EmpleadoMock(
    val idEmpleado: Long = 0L,
    val telefono: Long = 0L,
    val email: String = "",
    val cargo: String = "",
    val nombre: String = "",
    val local: LocalMock? = null
)

data class FacturaMock(
    val idFactura: Long = 0L,
    val monto: Double = 0.0,
    val fecha: String = "",
    val pendiente: Boolean = false,
    val tipoPago: String = "",
    val cliente: ClienteMock = ClienteMock(),
    val empleado: EmpleadoMock = EmpleadoMock(),
    val productos: List<ProductoMock> = emptyList(),
    val servicios: List<ServicioMock> = emptyList()
)

data class LocalMock(
    val idLocal: Long = 0L,
    val nombre: String = "",
    val direccion: String = "",
    val empleados: List<EmpleadoMock> = emptyList()
)

data class ProductoMock(
    val idProducto: Long = 0L,
    val nombre: String = "",
    val precioCompra: Double = 0.0,
    val precioVenta: Double = 0.0,
    val stock: Int = 0
)

data class ServicioMock(
    val idServicio: Long = 0L,
    val nombre: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val empleado: EmpleadoMock = EmpleadoMock()
)

data class UsuarioMock(
    val idUsuario: Long = 0L,
    val usuario: String = "",
    val contrasena: String = "",
    val empleado: EmpleadoMock = EmpleadoMock(),
    val rolUsuario: RolUsuario
)