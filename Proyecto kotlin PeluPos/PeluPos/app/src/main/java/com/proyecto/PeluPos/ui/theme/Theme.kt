package com.proyecto.PeluPos.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = PeluposLightPrimary,
    onPrimary = Color.White,
    secondary = PeluposLightAccent,
    onSecondary = Color.White,
    background = PeluposLightBackground,
    onBackground = PeluposLightTextPrimary,
    surface = PeluposLightSurface,
    onSurface = PeluposLightTextPrimary,
    surfaceVariant = PeluposLightSurface,
    onSurfaceVariant = PeluposLightTextSecondary,
    error = PeluposLightError,
    outline = PeluposLightBorder
)
private val DarkColorScheme = darkColorScheme(
    primary = PeluposDarkPrimary,
    onPrimary = Color.Black,
    secondary = PeluposDarkAccent,
    onSecondary = Color.Black,
    background = PeluposDarkBackground,
    onBackground = PeluposDarkTextPrimary,
    surface = PeluposDarkSurface,
    onSurface = PeluposDarkTextPrimary,
    surfaceVariant = PeluposDarkSurface,
    onSurfaceVariant = PeluposDarkTextSecondary,
    error = PeluposDarkError,
    outline = PeluposDarkBorder
)

@Composable
fun PeluPosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
object SidebarColors {
    val Background = Color(0xFF111827)
    val Content = Color.White
    val ContentSecondary = Color(0xFF9CA3AF)
    val Hover = Color(0xFF374151) // El color que buscabas
    val Separator = Color(0xFF4B5563)
}