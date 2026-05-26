package com.proyecto.PeluPos.ui.features.usuarios
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario
import com.proyecto.PeluPos.ui.features.servicios.ServiciosEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuariosScreen(
    state: UsuariosUiState,
    onEvent: (UsuariosEvent) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onEvent(UsuariosEvent.CargarUsuarios)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cuentas de Acceso", fontWeight = FontWeight.Bold) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(UsuariosEvent.PrepararNuevoUsuario)
                onNavigateToCreate()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Crear Cuenta")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            items(state.listaUsuarios) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    // 🚀 LA MAGIA RESPONSIVE: Detectamos el ancho disponible
                    BoxWithConstraints {
                        // Si el espacio es menor a 300dp (pantalla dividida/móvil estrecho)
                        val isCompact = this.maxWidth < 300.dp

                        if (isCompact) {
                            // --- DISEÑO VERTICAL (Para espacios estrechos) ---
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = MaterialTheme.shapes.extraLarge,
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier.size(40.dp) // Un poco más pequeño
                                    ) {
                                        Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(8.dp))
                                    }
                                    IconButton(onClick = { onNavigateToEdit(user.idUsuario) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Modificar")
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                // Textos con maxLines y TextOverflow para evitar cortes raros
                                Text(
                                    text = "@${user.usuario}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Vinculado a: ${user.empleado?.nombre ?: "Sin empleado asignado"}",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Surface(
                                    color = if (user.rolUsuario == RolUsuario.ADMINISTRADOR) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = user.rolUsuario.name,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        fontSize = 11.sp,
                                        color = if (user.rolUsuario == RolUsuario.ADMINISTRADOR) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        } else {
                            // --- DISEÑO HORIZONTAL ORIGINAL (Para tablets/pantalla completa) ---
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = MaterialTheme.shapes.extraLarge,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(12.dp))
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    // Añadido maxLines y Ellipsis también aquí por seguridad
                                    Text(text = "@${user.usuario}", fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(text = "Vinculado a: ${user.empleado?.nombre ?: "Sin empleado asignado"}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Surface(
                                        color = if (user.rolUsuario == RolUsuario.ADMINISTRADOR) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer,
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Text(
                                            text = user.rolUsuario.name,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            fontSize = 11.sp,
                                            color = if (user.rolUsuario == RolUsuario.ADMINISTRADOR) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                IconButton(onClick = { onNavigateToEdit(user.idUsuario) }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Modificar")
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}



//// ==========================================
//// PREVIEWS
//// ==========================================
//
//@Preview(showBackground = true, device = "id:pixel_8", name = "1. Lista de Cuentas")
//@Composable
//fun UsuariosScreenPreview() {
//    MaterialTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            // Pasamos un estado inicializado con nuestra lista falsa
//            UsuariosScreen(
//                state = UsuariosUiState(
//                    listaUsuarios = mockUsuarios
//                ),
//                onEvent = {}, // Lambda vacía porque en preview no hacemos nada
//                onNavigateToCreate = {},
//                onNavigateToEdit = {},
//                onBackClick = {}
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true, device = "id:pixel_8", name = "2. Crear Cuenta")
//@Composable
//fun UsuarioFormScreenCreatePreview() {
//    MaterialTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            // Estado simulando que estamos creando (todo vacío)
//            UsuarioFormScreen(
//                state = UsuariosUiState(
//                    empleadosDisponibles = listOf(mockEmpleado1, mockEmpleado2),
//                    editandoUsuarioId = null,
//                    formNombreUsuario = "",
//                    formContrasena = "",
//                    formRol = RolUsuario.EMPLEADO,
//                    formEmpleadoSeleccionado = null
//                ),
//                onEvent = {},
//                onBackClick = {},
//                onUsuarioGuardado = {}
//            )
//        }
//    }
//}
//
//@Preview(showBackground = true, device = "id:pixel_8", name = "3. Editar Cuenta")
//@Composable
//fun UsuarioFormScreenEditPreview() {
//    MaterialTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            // Estado simulando que ya hemos cargado los datos de un usuario para editarlos
//            UsuarioFormScreen(
//                state = UsuariosUiState(
//                    empleadosDisponibles = listOf(mockEmpleado1, mockEmpleado2),
//                    editandoUsuarioId = 1L,
//                    formNombreUsuario = "laura_admin",
//                    formContrasena = "1234",
//                    formRol = RolUsuario.ADMINISTRADOR,
//                    formEmpleadoSeleccionado = mockEmpleado1
//                ),
//                onEvent = {},
//                onBackClick = {},
//                onUsuarioGuardado = {}
//            )
//        }
//    }
//}