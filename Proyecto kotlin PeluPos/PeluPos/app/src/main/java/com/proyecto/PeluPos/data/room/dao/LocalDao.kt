package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.LocalEmpleadoCrossRef
import com.proyecto.PeluPos.data.room.entity.LocalEntity
import com.proyecto.PeluPos.data.room.entity.LocalWithEmpleados
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    // Obtener todos los locales con sus empleados
    @Transaction
    @Query("SELECT * FROM locales ORDER BY nombre ASC")
    fun getAllLocalesFlow(): Flow<List<LocalWithEmpleados>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocal(local: LocalEntity)

    @Update
    suspend fun updateLocal(local: LocalEntity)

    @Delete
    suspend fun deleteLocal(local: LocalEntity)

    // Insertar relación local-empleado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalEmpleadoCrossRef(crossRef: LocalEmpleadoCrossRef)

    @Delete
    suspend fun deleteLocalEmpleadoCrossRef(crossRef: LocalEmpleadoCrossRef)
}