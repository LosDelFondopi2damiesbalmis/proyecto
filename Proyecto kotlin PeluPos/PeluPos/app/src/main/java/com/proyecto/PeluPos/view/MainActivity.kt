package com.proyecto.PeluPos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.proyecto.PeluPos.ui.dashboard.MainScreen
import com.proyecto.PeluPos.ui.theme.PeluPosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeluPosTheme {
                MainScreen()

            }
        }
    }
}



