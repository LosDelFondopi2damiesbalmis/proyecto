package com.proyecto.PeluPos.data.mocks

import android.content.Context
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
        val response = authService.login(request)

        // Si el token nos llega bien (como te acaba de pasar en el Logcat)
        if (!response.jwtToken.isNullOrEmpty()) {
            prefs.edit().apply {
                putString("jwt_token", response.jwtToken)
                putString("usuario", response.usuario)
                putString("rol", response.rolUsuario)
                // 🚀 NUEVO: Guardamos también los IDs
                putString("idUsuario", response.idUsuario)
                putString("idEmpleado", response.idEmpleado)
                apply()
            }
        }

        return@withContext response
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