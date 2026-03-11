package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.LocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    @Query("SELECT * FROM locales ORDER BY nombre ASC")
    fun getAllLocalesFlow(): Flow<List<LocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocal(local: LocalEntity)

    @Update
    suspend fun updateLocal(local: LocalEntity)

    @Delete
    suspend fun deleteLocal(local: LocalEntity)
}