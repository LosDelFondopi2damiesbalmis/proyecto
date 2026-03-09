package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.LocalEntity

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(local: LocalEntity)
    @Update suspend fun update(local: LocalEntity)
    @Delete suspend fun delete(local: LocalEntity)
    @Query("SELECT * FROM locales") suspend fun getAll(): List<LocalEntity>
    @Query("SELECT * FROM locales WHERE idLocal = :id") suspend fun getById(id: Long): LocalEntity?
}