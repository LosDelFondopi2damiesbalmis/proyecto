package com.proyecto.PeluPos.data.mocks.local

import com.proyecto.PeluPos.data.mocks.LocalMock
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleados
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleadosMock
import com.proyecto.PeluPos.models.Local

fun Local.toLocalMock() =
    LocalMock(idLocal, nombre, direccion, empleadoCollection.toEmpleadosMock())

fun List<Local>.toLocalesMock() =
    map { it.toLocalMock() }

fun LocalMock.toLocal() =
    Local(idLocal, nombre, direccion, empleados.toEmpleados().toMutableList())

fun List<LocalMock>.toLocales() =
    map { it.toLocal() }
