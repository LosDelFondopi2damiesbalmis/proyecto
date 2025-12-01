package com.proyecto.PeluPos.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.PeluPos.ui.theme.SidebarColorHover
import com.proyecto.PeluPos.ui.theme.SidebarColorText


@Composable
fun NavButton(
    text: String,
    onClick: () -> Unit,
    isActive: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (isActive) SidebarColorHover else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp), // Padding interno del botón
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = SidebarColorText,
            fontSize = 14.sp
        )
    }
}