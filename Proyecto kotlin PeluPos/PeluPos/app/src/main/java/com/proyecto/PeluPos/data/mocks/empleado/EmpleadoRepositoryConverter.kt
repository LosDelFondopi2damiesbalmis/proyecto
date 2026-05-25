package com.proyecto.PeluPos.data.mocks.empleado

import com.proyecto.PeluPos.data.mocks.EmpleadoMock
import com.proyecto.PeluPos.data.mocks.local.toLocal
import com.proyecto.PeluPos.data.mocks.local.toLocalMock
import com.proyecto.PeluPos.models.Empleado


fun Empleado.toEmpleadoMock() =
    EmpleadoMock(idEmpleado, telefono, email, cargo, nombre, idLocal?.toLocalMock())

fun List<Empleado>.toEmpleadosMock(): List<EmpleadoMock> =
    map { it.toEmpleadoMock() }

fun EmpleadoMock.toEmpleado() =
    Empleado(idEmpleado, telefono, email, cargo, nombre, local?.toLocal())

fun List<EmpleadoMock>.toEmpleados(): List<Empleado> =
    map { it.toEmpleado() }
