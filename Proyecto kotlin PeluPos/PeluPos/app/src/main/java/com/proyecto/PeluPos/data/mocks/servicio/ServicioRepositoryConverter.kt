package com.proyecto.PeluPos.data.mocks.servicio

import com.proyecto.PeluPos.data.mocks.ServicioMock
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleado
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleadoMock
import com.proyecto.PeluPos.models.Servicio

fun Servicio.toServicioMock() =
    ServicioMock(idServicio, nombre, precio, descripcion, empleado.toEmpleadoMock())

fun List<Servicio>.toServiciosMock() =
    map { it.toServicioMock() }

fun ServicioMock.toServicio() =
    Servicio(idServicio, nombre, precio, descripcion, empleado.toEmpleado())

fun List<ServicioMock>.toServicios() =
    map { it.toServicio() }
