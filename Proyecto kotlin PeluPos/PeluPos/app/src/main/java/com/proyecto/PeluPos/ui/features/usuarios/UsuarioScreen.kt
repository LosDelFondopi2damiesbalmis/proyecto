package com.proyecto.PeluPos.ui.features.usuarios
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.Local
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuariosScreen(
    usuarios: List<Usuario>,
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cuentas de Acceso", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Volver") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
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

            items(usuarios) { user ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
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
                            // Nombre de usuario de login
                            Text(text = "@${user.usuario}", fontWeight = FontWeight.Bold, fontSize = 16.sp)

                            // A qué empleado pertenece
                            Text(text = "Vinculado a: ${user.empleado.nombre}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

                            Spacer(modifier = Modifier.height(6.dp))

                            // Badge para el Rol
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
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}
private val mockLocal = Local(1L, "Sede Central", "Calle Mayor, 10")
private val mockEmpleado1 =
    Empleado(1L, 600123456L, "admin@pelupos.com", "Dueño", "Laura Gómez", mockLocal)
private val mockEmpleado2 = Empleado(2L, 611987654L, "carlos@pelupos.com", "Barbero", "Carlos Ruiz", mockLocal)

private val mockUsuarios = listOf(
    Usuario(1L, "laura_admin", "1234", mockEmpleado1, RolUsuario.ADMINISTRADOR),
    Usuario(2L, "carlos_tpv", "1234", mockEmpleado2, RolUsuario.EMPLEADO)
)

@Preview(showBackground = true, device = "id:pixel_5", name = "1. Lista de Cuentas")
@Composable
fun UsuariosScreenPreview() {
    MaterialTheme {
        Surface {
            UsuariosScreen(
                usuarios = mockUsuarios,
                onNavigateToCreate = {},
                onNavigateToEdit = {},
                onBackClick = {}
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5", name = "2. Crear Cuenta")
@Composable
fun UsuarioFormScreenCreatePreview() {
    MaterialTheme {
        Surface {
            UsuarioFormScreen(
                usuarioId = null,
                empleadosDisponibles = listOf(mockEmpleado1, mockEmpleado2),
                onBackClick = {},
                onSaveClick = {}
            )
        }
    }
}