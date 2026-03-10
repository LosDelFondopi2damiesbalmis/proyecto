package com.proyecto.PeluPos.data.mocks.usuario

import com.proyecto.PeluPos.data.mocks.EmpleadoMock
import com.proyecto.PeluPos.data.mocks.UsuarioMock
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario

class UsuarioDaoMock {

    private val usuarios = mutableListOf<UsuarioMock>()
   /* private val usuarios = mutableListOf(
        UsuarioMock(1, "admin", "1234", EmpleadoMock(1), rolUsuario = RolUsuario.ADMINISTRADOR),
        UsuarioMock(2, "carlos", "abcd", EmpleadoMock(2), rolUsuario = RolUsuario.EMPLEADO)
    )*/

    fun getAll(): List<UsuarioMock> = usuarios.toList()

    fun get(id: Long): UsuarioMock? =
        usuarios.find { it.idUsuario == id }

    fun insert(u: UsuarioMock): Boolean {
        if (usuarios.any { it.usuario == u.usuario }) return false

        (1L..usuarios.size + 1L).forEach { i ->
            if (usuarios.none { it.idUsuario == i }) {
                usuarios.add(u.copy(idUsuario = i))
                return true
            }
        }
        return false
    }

    fun update(u: UsuarioMock): Boolean {
        val index = usuarios.indexOfFirst { it.idUsuario == u.idUsuario }
        if (index == -1) return false
        usuarios[index] = u
        return true
    }

    fun delete(id: Long): Boolean {
        val index = usuarios.indexOfFirst { it.idUsuario == id }
        if (index == -1) return false
        usuarios.removeAt(index)
        return true
    }
}
