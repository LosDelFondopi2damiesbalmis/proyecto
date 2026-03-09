package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.EmpleadoEntity

@Dao
interface EmpleadoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(empleado: EmpleadoEntity)
    @Update suspend fun update(empleado: EmpleadoEntity)
    @Delete suspend fun delete(empleado: EmpleadoEntity)
    @Query("SELECT * FROM empleados") suspend fun getAll(): List<EmpleadoEntity>
    @Query("SELECT * FROM empleados WHERE idEmpleado = :id") suspend fun getById(id: Long): EmpleadoEntity?
}