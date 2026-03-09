package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.ClienteEntity

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(cliente: ClienteEntity)
    @Update suspend fun update(cliente: ClienteEntity)
    @Delete suspend fun delete(cliente: ClienteEntity)
    @Query("SELECT * FROM clientes") suspend fun getAll(): List<ClienteEntity>
    @Query("SELECT * FROM clientes WHERE idCliente = :id") suspend fun getById(id: Long): ClienteEntity?
}