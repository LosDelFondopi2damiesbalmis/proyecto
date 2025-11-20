package com.proyecto.PeluPos.data.mocks.local

import com.proyecto.PeluPos.models.Local

class LocalRepository() {
    private val localDaoMock: LocalDaoMock = LocalDaoMock()
    fun getLocales(): List<Local> =
        localDaoMock.getAll().toLocales()

    fun getLocal(id: Long): Local? =
        localDaoMock.get(id)?.toLocal()

    fun insert(local: Local): Boolean =
        localDaoMock.insert(local.toLocalMock())

    fun updateLocal(local: Local): Boolean =
        localDaoMock.update(local.toLocalMock())

    fun delete(id: Long): Boolean =
        localDaoMock.delete(id)
}
