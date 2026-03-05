package com.proyecto.PeluPos.di

import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleApp {

    @Provides
    @Singleton
    fun provideProductoRepository(): ProductoRepository {
        // Como tu repositorio actual no pide parámetros en el constructor,
        // simplemente lo instanciamos aquí.
        return ProductoRepository()
    }
    @Provides
    @Singleton
    fun provideCarritoRepository(): CarritoRepository {
        return CarritoRepository()
    }

    @Provides
    @Singleton
    fun provideServicioRepository(): ServicioRepository {
        return ServicioRepository()
    }
}