package com.proyecto.PeluPos.data.mocks.usuario

import com.proyecto.PeluPos.models.Usuario
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor() {
    private val usuarioDaoMock: UsuarioDaoMock = UsuarioDaoMock()

    fun getUsuarios(): List<Usuario> =
        usuarioDaoMock.getAll().toUsuarios()

    fun getUsuario(id: Long): Usuario? =
        usuarioDaoMock.get(id)?.toUsuario()

    fun insert(usuario: Usuario): Boolean =
        usuarioDaoMock.insert(usuario.toUsuarioMock())

    fun updateUsuario(usuario: Usuario): Boolean =
        usuarioDaoMock.update(usuario.toUsuarioMock())

    fun delete(id: Long): Boolean =
        usuarioDaoMock.delete(id)
}
