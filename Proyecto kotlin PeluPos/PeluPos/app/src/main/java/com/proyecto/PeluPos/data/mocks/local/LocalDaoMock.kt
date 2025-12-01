package com.proyecto.PeluPos.data.mocks.local

import com.proyecto.PeluPos.data.mocks.LocalMock

class LocalDaoMock {

    private val locales = mutableListOf(
        LocalMock(1, "Local Centro", "Av. Principal 123"),
        LocalMock(2, "Local Norte", "Calle Secundaria 45")
    )

    fun getAll(): List<LocalMock> = locales.toList()

    fun get(id: Long): LocalMock? =
        locales.find { it.idLocal == id }

    fun insert(l: LocalMock): Boolean {
        (1L..locales.size + 1L).forEach { i ->
            if (locales.none { it.idLocal == i }) {
                locales.add(l.copy(idLocal = i))
                return true
            }
        }
        return false
    }

    fun update(l: LocalMock): Boolean {
        val index = locales.indexOfFirst { it.idLocal == l.idLocal }
        if (index == -1) return false
        locales[index] = l
        return true
    }

    fun delete(id: Long): Boolean {
        val index = locales.indexOfFirst { it.idLocal == id }
        if (index == -1) return false
        locales.removeAt(index)
        return true
    }
}
