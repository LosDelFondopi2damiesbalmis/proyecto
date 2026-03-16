package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.ServicioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(servicio: ServicioEntity)
    @Update suspend fun update(servicio: ServicioEntity)
    @Delete suspend fun delete(servicio: ServicioEntity)
    @Query("SELECT * FROM servicios") suspend fun getAll(): List<ServicioEntity>
    @Query("SELECT * FROM servicios")
    fun getAllFlow(): Flow<List<ServicioEntity>>
}