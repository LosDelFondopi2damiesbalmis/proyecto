package com.proyecto.PeluPos.data.mocks

import android.content.Context
import android.util.Log
import com.proyecto.PeluPos.data.services.autentication.ApiServicesException
import com.proyecto.PeluPos.data.services.autentication.AuthServiceImplementation
import com.proyecto.PeluPos.models.LoginRequest
import com.proyecto.PeluPos.models.LoginResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    private val authService: AuthServiceImplementation,
    // 🚀 Inyectamos el contexto de Android para poder usar el almacenamiento
    @ApplicationContext private val context: Context
) {
    // 🚀 Creamos un archivo en el disco del móvil llamado "mis_sesiones"
    private val prefs = context.getSharedPreferences("mis_sesiones", Context.MODE_PRIVATE)

    suspend fun login(usuario: String, contrasena: String): LoginResponse = withContext(Dispatchers.IO) {
        val request = LoginRequest(usuario = usuario, contrasena = contrasena)

        try {
            // 1. Llamamos a Tomcat. Como no usas "Response<>", esto devuelve los datos directamente
            val dato = authService.login(request)

            // 2. Comprobamos que el token exista realmente
            if (dato.jwtToken.isNullOrEmpty()) {
                throw ApiServicesException("Credenciales incorrectas")
            }

            // 3. ✅ ÉXITO: Guardamos los datos
            prefs.edit().apply {
                putString("jwt_token", dato.jwtToken)
                putString("usuario", dato.usuario)
                putString("rol", dato.rolUsuario)
                putString("idUsuario", dato.idUsuario)
                putString("idEmpleado", dato.idEmpleado)
                apply()
            }

            return@withContext dato

        } catch (e: retrofit2.HttpException) {
            // 4. ❌ FALLO HTTP (Ej: Tomcat devuelve 401 Unauthorized o 403)
            // Como Retrofit salta aquí directamente si hay error, limpiamos la memoria
            prefs.edit().clear().apply()

            // Podemos leer el errorBody desde la excepción
            val bodyError = e.response()?.errorBody()?.string() ?: ""
            Log.e("LOGIN", "Error (código ${e.code()}): \n$bodyError")

            throw ApiServicesException("Contraseña o usuario incorrectos")

        } catch (e: Exception) {
            // 5. ❌ FALLO DE RED / OTRA EXCEPCIÓN: ¡LIMPIAMOS MEMORIA POR SEGURIDAD!
            prefs.edit().clear().apply()

            Log.e("LOGIN", "Error de red o ejecución: ${e.localizedMessage}")

            // Si es nuestra propia excepción (la del token vacío), la dejamos pasar
            if (e is ApiServicesException) throw e

            // Si es otro error (ej. Timeout, servidor apagado)
            throw ApiServicesException("No se pudo conectar con el servidor. Comprueba tu conexión.")
        }
    }

    // 🚀 EL DASHBOARD AHORA LEERÁ SIEMPRE DEL DISCO, JAMÁS SERÁ NULL
    fun getUsuarioActual(): LoginResponse? {
        val tokenGuardado = prefs.getString("jwt_token", null)

        // Si no hay token en el disco, devolvemos null
        if (tokenGuardado.isNullOrEmpty()) return null

        // Si hay token, reconstruimos el usuario para el Dashboard
        // Si hay token, reconstruimos el usuario completo para el Dashboard
        return LoginResponse(
            jwtToken = tokenGuardado,
            usuario = prefs.getString("usuario", "Usuario Desconocido"),
            rolUsuario = prefs.getString("rol", "Sin Rol"),
            // 🚀 NUEVO: Leemos los IDs guardados. Si no hay, devolvemos null
            idUsuario = prefs.getString("idUsuario", null),
            idEmpleado = prefs.getString("idEmpleado", null),
            // Kotlin te pide el parámetro 'mensaje', le pasamos null o vacío
            mensaje = ""
        )
    }

    suspend fun cerrarSesion() = withContext(Dispatchers.IO) {
        val tokenParaBorrar = prefs.getString("jwt_token", null)

        try {
            if (!tokenParaBorrar.isNullOrEmpty()) {
                authService.logout()
            }
        } catch (e: Exception) {
            println("Aviso: No se pudo avisar a Tomcat -> ${e.message}")
        } finally {
            // 🚀 AL CERRAR SESIÓN, BORRAMOS EL DISCO
            prefs.edit().clear().apply()
        }
    }
}