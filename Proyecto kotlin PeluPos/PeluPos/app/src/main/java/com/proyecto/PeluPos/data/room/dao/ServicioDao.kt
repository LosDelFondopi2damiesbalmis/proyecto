package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.ServicioEntity

@Dao
interface ServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(servicio: ServicioEntity)
    @Update suspend fun update(servicio: ServicioEntity)
    @Delete suspend fun delete(servicio: ServicioEntity)
    @Query("SELECT * FROM servicios") suspend fun getAll(): List<ServicioEntity>
}