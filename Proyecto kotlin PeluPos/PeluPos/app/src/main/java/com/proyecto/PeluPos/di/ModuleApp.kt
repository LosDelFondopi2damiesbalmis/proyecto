package com.proyecto.PeluPos.di

import com.proyecto.PeluPos.data.mocks.CarritoRepository
import com.proyecto.PeluPos.data.mocks.SessionRepository
import com.proyecto.PeluPos.data.mocks.cliente.ClienteRepository
import com.proyecto.PeluPos.data.mocks.empleado.EmpleadoRepository
import com.proyecto.PeluPos.data.mocks.factura.FacturaRepository
import com.proyecto.PeluPos.data.mocks.local.LocalRepository
import com.proyecto.PeluPos.data.mocks.producto.ProductoRepository
import com.proyecto.PeluPos.data.mocks.servicio.ServicioRepository
import com.proyecto.PeluPos.data.mocks.usuario.UsuarioRepository
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
    fun provideUsuarioRepository(): UsuarioRepository {
        return UsuarioRepository()
    }
    @Provides
    @Singleton
    fun provideClienteRepository(): ClienteRepository {
        return ClienteRepository()
    }

    @Provides
    @Singleton
    fun provideSessionRepository(): SessionRepository {
        return SessionRepository()
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
    @Provides
    @Singleton
    fun provideEmpleadoRepository(): EmpleadoRepository {
        return EmpleadoRepository()
    }
    @Provides
    @Singleton
    fun provideLocalesRepository(): LocalRepository {
        return LocalRepository()
    }
    @Provides
    @Singleton
    fun provideFacturasRepository(): FacturaRepository {
        return FacturaRepository()
    }


}