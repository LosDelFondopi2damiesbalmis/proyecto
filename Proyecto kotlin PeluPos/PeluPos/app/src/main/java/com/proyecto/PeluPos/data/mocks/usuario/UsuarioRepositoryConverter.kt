package com.proyecto.PeluPos.data.mocks.usuario

import com.proyecto.PeluPos.data.mocks.UsuarioMock
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleado
import com.proyecto.PeluPos.data.mocks.empleado.toEmpleadoMock
import com.proyecto.PeluPos.models.Usuario

fun Usuario.toUsuarioMock() =
    UsuarioMock(idUsuario, usuario, contrasena, empleado.toEmpleadoMock(), rolUsuario)

fun List<Usuario>.toUsuariosMock() =
    map { it.toUsuarioMock() }

fun UsuarioMock.toUsuario() =
    Usuario(idUsuario, usuario, contrasena, empleado.toEmpleado(), rolUsuario = rolUsuario)

fun List<UsuarioMock>.toUsuarios() =
    map { it.toUsuario() }
