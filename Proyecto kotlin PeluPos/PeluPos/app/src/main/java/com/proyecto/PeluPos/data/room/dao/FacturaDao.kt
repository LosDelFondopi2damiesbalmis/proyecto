package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.FacturaEntity

@Dao
interface FacturaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(factura: FacturaEntity)

    @Update
    suspend fun update(factura: FacturaEntity)

    @Delete
    suspend fun delete(factura: FacturaEntity)

    //Flow reactivo
    @Query("SELECT * FROM facturas")
    fun getAllFlow(): kotlinx.coroutines.flow.Flow<List<FacturaEntity>>

    @Query("SELECT * FROM facturas WHERE idFactura = :id")
    suspend fun getById(id: Long): FacturaEntity?
}