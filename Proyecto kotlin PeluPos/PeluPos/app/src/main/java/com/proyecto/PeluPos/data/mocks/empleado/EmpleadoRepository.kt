package com.proyecto.PeluPos.data.mocks.empleado

import com.proyecto.PeluPos.models.Empleado
import javax.inject.Singleton

@Singleton
class EmpleadoRepository() {
    private val empleadoDaoMock: EmpleadoDaoMock = EmpleadoDaoMock()
    fun getEmpleados(): List<Empleado> =
        empleadoDaoMock.getAll().toEmpleados()

    fun getEmpleado(id: Long): Empleado? =
        empleadoDaoMock.get(id)?.toEmpleado()

    fun insert(empleado: Empleado): Boolean =
        empleadoDaoMock.insert(empleado.toEmpleadoMock())

    fun updateEmpleado(empleado: Empleado): Boolean =
        empleadoDaoMock.update(empleado.toEmpleadoMock())

    fun delete(id: Long): Boolean =
        empleadoDaoMock.delete(id)
}
