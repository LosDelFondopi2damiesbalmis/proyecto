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
import com.proyecto.PeluPos.data.services.autentication.AuthInterceptor
import com.proyecto.PeluPos.data.services.autentication.AuthService
import com.proyecto.PeluPos.data.services.empleados.EmpleadoService
import com.proyecto.PeluPos.data.services.productos.ProductoService
import com.proyecto.PeluPos.data.services.usuarios.UsuarioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleApp {

    @Provides
    @Singleton
    fun provideProductoService(retrofit: Retrofit): ProductoService {
        return retrofit.create(ProductoService::class.java)
    }
    @Provides
    @Singleton
    fun provideUsuarioService(retrofit: Retrofit): UsuarioService {
        return retrofit.create(UsuarioService::class.java)
    }
    @Provides
    @Singleton
    fun provideClienteRepository(): ClienteRepository {
        return ClienteRepository()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor // 👈 1. Hilt nos inyecta tu interceptor aquí
    ): OkHttpClient {
        // Esto te permitirá ver las peticiones en el Logcat de Android Studio
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val timeout = 10L

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // El que imprime en el Logcat
            .addInterceptor(authInterceptor)    // 👈 2. ¡EL QUE PONE EL TOKEN!
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            // IMPORTANTE: Cambia "api/" por la ruta base real de tu API en NetBeans [cite: 15]
            .baseUrl("http://192.168.1.109:8080/pelupos/servicio/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
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
    fun provideEmpleadoService(retrofit: Retrofit): EmpleadoService {
        return retrofit.create(EmpleadoService::class.java)
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