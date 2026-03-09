package com.proyecto.PeluPos.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true) val idProducto: Long = 0L,
    var nombre: String,
    var precioCompra: Double,
    var precioVenta: Double,
    var stock: Int
)