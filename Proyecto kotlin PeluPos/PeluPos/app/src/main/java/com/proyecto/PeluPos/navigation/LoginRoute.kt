package com.proyecto.PeluPos.navigation

import kotlinx.serialization.Serializable
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.proyecto.PeluPos.ui.features.login.AccessDeniedScreen
import com.proyecto.PeluPos.ui.features.login.FakePermissionsScreen
import com.proyecto.PeluPos.ui.features.login.LoginEvent
import com.proyecto.PeluPos.ui.features.login.LoginScreen
import com.proyecto.PeluPos.ui.features.login.LoginViewModel

@Serializable
object LoginRoute

@Serializable
object FakePermissionsRoute

@Serializable
object AccessDeniedRoute

fun NavGraphBuilder.loginDestination(
    navigateToHome: () -> Unit,
    onExitApp: () -> Unit,
    navController: NavHostController
) {
    composable<LoginRoute> {
        val vm = hiltViewModel<LoginViewModel>()
        val state by vm.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        // --- OBSERVADOR DE ÉXITO ---
        // Se ejecuta automáticamente cada vez que cambia 'state.isLoginSuccessful'
        LaunchedEffect(state.isLoginSuccessful) {
            if (state.isLoginSuccessful) {
                // 1. Mostramos el mensaje de éxito
                Toast.makeText(context, "¡Sesión iniciada correctamente!", Toast.LENGTH_SHORT).show()

                navigateToHome()
            }
        }

        LaunchedEffect(Unit) {
            vm.onEvent(LoginEvent.CargarUsuarios)
        }

        LoginScreen(
            users = state.usuariosDisponibles,
            errorMessage = state.errorMessage,
            onLoginClick = { usuario, contrasena ->
                vm.onEvent(LoginEvent.OnLoginClick(usuario, contrasena))
            },
            onCancelClick = onExitApp,
            onNoAccountClick = { navController.navigate(FakePermissionsRoute) },
        )
    }

    composable<FakePermissionsRoute> {
        FakePermissionsScreen(
            onAcceptClick = {
                navController.navigate(UsuarioFormRoute(idUsuario = null)) {
                    popUpTo(FakePermissionsRoute) { inclusive = true } // Borramos esta pantalla del historial
                }
            },
            onDenyClick = {
                navController.navigate(AccessDeniedRoute) {
                    popUpTo(FakePermissionsRoute) { inclusive = true }
                }
            }
        )
    }

    composable<AccessDeniedRoute> {
        AccessDeniedScreen(
            onBackToLoginClick = {
                navController.navigate(LoginRoute) {
                    popUpTo(0)
                }
            }
        )
    }
}