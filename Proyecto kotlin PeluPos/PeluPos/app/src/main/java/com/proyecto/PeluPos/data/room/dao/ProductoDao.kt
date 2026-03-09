package com.proyecto.PeluPos.data.room.dao

import androidx.room.*
import com.proyecto.PeluPos.data.room.entity.ProductoEntity

@Dao
interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(producto: ProductoEntity)
    @Update suspend fun update(producto: ProductoEntity)
    @Delete suspend fun delete(producto: ProductoEntity)
    @Query("SELECT * FROM productos") suspend fun getAll(): List<ProductoEntity>
}