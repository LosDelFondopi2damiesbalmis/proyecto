package com.proyecto.PeluPos.navigation

import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.features.usuarios.UsuarioFormScreen
import com.proyecto.PeluPos.ui.features.usuarios.UsuariosScreen
import com.proyecto.PeluPos.ui.features.usuarios.UsuariosViewModel

import androidx.navigation.toRoute

// ==========================================
// RUTAS
// ==========================================
@Serializable
object UsuariosListRoute

@Serializable
data class UsuarioFormRoute(
    val idUsuario: Long? = null // 👈 null = Crear, Número = Editar
)

// ==========================================
// FUNCIÓN DESTINATION
// ==========================================
fun NavGraphBuilder.usuariosDestination(
    navigateToForm: (Long?) -> Unit, // 👈 Ahora acepta el ID opcional
    onBack: () -> Unit // Esta es la función clave para volver atrás
) {
    // ==========================================
    // 1. LISTA DE USUARIOS
    // ==========================================
    composable<UsuariosListRoute> {
        val vm = hiltViewModel<UsuariosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        UsuariosScreen(
            state = state,
            onEvent = vm::onEvent,
            // 🚀 CREAR: Mandamos 'null' a la ruta
            onNavigateToCreate = { navigateToForm(null) },
            // 🚀 EDITAR: Mandamos el ID real del usuario
            onNavigateToEdit = { idUsuario -> navigateToForm(idUsuario) },
            onBackClick = onBack
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<UsuarioFormRoute> { backStackEntry ->
        // Extraemos la ruta para que Hilt se la pase al ViewModel
        val routeData = backStackEntry.toRoute<UsuarioFormRoute>()

        val vm = hiltViewModel<UsuariosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        UsuarioFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onBackClick = onBack,
            onUsuarioGuardado = onBack
        )
    }
}