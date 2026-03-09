package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.ClienteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    fun getAllClientesFlow(): Flow<List<ClienteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(cliente: ClienteEntity)

    @Update
    suspend fun updateCliente(cliente: ClienteEntity)

    @Delete
    suspend fun deleteCliente(cliente: ClienteEntity)
}