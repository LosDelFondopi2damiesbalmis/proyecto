package com.proyecto.PeluPos.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyecto.PeluPos.ui.theme.SidebarColors

@Composable
fun NavSeparator() {
    Spacer(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(SidebarColors.Separator)
    )
}