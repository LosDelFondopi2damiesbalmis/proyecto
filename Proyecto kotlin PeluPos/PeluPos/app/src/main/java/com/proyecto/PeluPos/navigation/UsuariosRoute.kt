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

@Serializable
object UsuariosListRoute

@Serializable
object UsuarioFormRoute

fun NavGraphBuilder.usuariosDestination(
    navigateToForm: () -> Unit,
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
            // Las llamadas ya están configuradas en tu pantalla para
            // mandar el evento al ViewModel y luego navegar:
            onNavigateToCreate = navigateToForm,
            onNavigateToEdit = navigateToForm, // Usamos la misma ruta para editar
            onBackClick = onBack // Vuelve al menú principal o donde estuvieras
        )
    }

    // ==========================================
    // 2. FORMULARIO (Crear/Editar)
    // ==========================================
    composable<UsuarioFormRoute> {
        val vm = hiltViewModel<UsuariosViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()

        UsuarioFormScreen(
            state = state,
            onEvent = vm::onEvent,
            onBackClick = onBack, // Si le da a la flecha de volver (cancela)
            onUsuarioGuardado = onBack // Si le da a guardar (termina)
        )
    }

}
