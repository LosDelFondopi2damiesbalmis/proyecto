package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(usuario: UsuarioEntity)
    @Update suspend fun update(usuario: UsuarioEntity)
    @Delete suspend fun delete(usuario: UsuarioEntity)
    @Query("SELECT * FROM usuarios") suspend fun getAll(): List<UsuarioEntity>
    @Query("SELECT * FROM usuarios WHERE idUsuario = :id") suspend fun getById(id: Long): UsuarioEntity?
    @Query("SELECT * FROM usuarios")
    fun getAllFlow(): kotlinx.coroutines.flow.Flow<List<UsuarioEntity>>
}