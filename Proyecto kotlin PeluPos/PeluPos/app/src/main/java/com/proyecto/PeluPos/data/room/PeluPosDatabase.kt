package com.proyecto.PeluPos.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyecto.PeluPos.data.room.dao.*
import com.proyecto.PeluPos.data.room.entity.*

@Database(
    entities = [
        ClienteEntity::class,
        EmpleadoEntity::class,
        LocalEntity::class,
        ProductoEntity::class,
        ServicioEntity::class,
        FacturaEntity::class,
        UsuarioEntity::class
    ],
    version = 1
)
abstract class PeluPosDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun localDao(): LocalDao
    abstract fun productoDao(): ProductoDao
    abstract fun servicioDao(): ServicioDao
    abstract fun facturaDao(): FacturaDao
    abstract fun usuarioDao(): UsuarioDao
}