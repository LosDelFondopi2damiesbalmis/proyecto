package com.proyecto.PeluPos.ui.features.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.models.Empleado
import com.proyecto.PeluPos.models.RolUsuario
import com.proyecto.PeluPos.models.Usuario

// Modelo de datos equivalente a tu Binding

@Composable
fun LoginScreen(
    users: List<Usuario>,
    errorMessage: String? = null,
    onLoginClick: (Usuario?, String) -> Unit,
    onCancelClick: () -> Unit
) {
    // Estados equivalentes a tu SelectedItem y al contenido del PasswordBox
    var selectedUser by remember { mutableStateOf<Usuario?>(null) }
    var password by remember { mutableStateOf("") }

    // Grid principal (equivalente a Grid con 3 Rows)
    Column(
        modifier = Modifier
            .fillMaxSize() // O un tamaño fijo si es para Desktop: .size(520.dp, 460.dp)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(24.dp)
    ) {

        // --- ROW 0: Encabezado (StackPanel) ---
        Column(modifier = Modifier.padding(bottom = 14.dp)) {
            Text(
                text = "Iniciar sesión",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Selecciona tu usuario e introduce la contraseña.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // --- ROW 1: Contenedor Principal (Border con Grid interno) ---
        Surface(
            modifier = Modifier
                .weight(1f) // Esto hace que ocupe el espacio restante (equivalente a Height="*")
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(12.dp)) {

                // 1. Lista de Usuarios (ListView)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Para que la lista scrollee y deje espacio a la contraseña
                        .padding(bottom = 10.dp)
                ) {
                    items(users) { user ->
                        UserItem(
                            user = user,
                            isSelected = user == selectedUser,
                            onClick = { selectedUser = user }
                        )
                    }
                }

                // 2. Contraseña (StackPanel con PasswordBox)
                Column(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(
                        text = "Contraseña",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(4.dp) // Ajuste fino del borde
                    )
                }

                // 3. Mensaje de Error (TextBlock)
                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }

        // --- ROW 2: Botones (StackPanel Horizontal) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onCancelClick,
                modifier = Modifier
                    .width(110.dp)
                    .height(38.dp)
                    .padding(end = 10.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Salir")
            }

            Button(
                onClick = { onLoginClick(selectedUser, password) },
                modifier = Modifier
                    .width(110.dp)
                    .height(38.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Entrar")
            }
        }
    }
}

// DataTemplate del ListView extraído a su propio Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    user: Usuario,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Cambiamos el color si está seleccionado para dar feedback visual
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant

    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, borderColor),
        color = backgroundColor
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = user.empleado.nombre,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Rol: ${user.rolUsuario.name.lowercase().replaceFirstChar { it.uppercase() }}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Preview(showBackground = true, device = "id:pixel_5", name = "Login Normal")
@Composable
fun LoginScreenPreview() {
    // Datos mockeados usando tu clase Empleado exacta
    val usuariosDePrueba = listOf(
        Usuario(
            idUsuario = 1,
            usuario = "admin",
            contrasena = "1234",
            empleado = Empleado(
                idEmpleado = 101,
                telefono = 600123456L,
                email = "ana@pelupos.com",
                cargo = "Gerente",
                nombre = "Ana García",
                local = null
            ),
            rolUsuario = RolUsuario.ADMINISTRADOR
        ),
        Usuario(
            idUsuario = 2,
            usuario = "mlopez",
            contrasena = "1234",
            empleado = Empleado(
                idEmpleado = 102,
                telefono = 600654321L,
                email = "marcos@pelupos.com",
                cargo = "Estilista",
                nombre = "Marcos López",
                local = null
            ),
            rolUsuario = RolUsuario.MANAGER
        ),
        Usuario(
            idUsuario = 3,
            usuario = "jdoe",
            contrasena = "1234",
            empleado = Empleado(
                idEmpleado = 103,
                telefono = 600987654L,
                email = "john@pelupos.com",
                cargo = "Ayudante",
                nombre = "John Doe",
                local = null
            ),
            rolUsuario = RolUsuario.EMPLEADO
        )
    )

    MaterialTheme {
        LoginScreen(
            users = usuariosDePrueba,
            errorMessage = null,
            onLoginClick = { _, _ -> },
            onCancelClick = { }
        )
    }
}