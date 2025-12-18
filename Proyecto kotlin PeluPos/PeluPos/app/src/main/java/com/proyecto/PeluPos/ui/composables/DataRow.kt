package com.proyecto.PeluPos.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DataRow(
    col1: String,
    col2: String,
    col3: String? = null,
    col4: String? = null,
    isHeader: Boolean = false,
    weights: List<Float>
) {
    val textColor = if (isHeader) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
    val weightFont = if (isHeader) FontWeight.Bold else FontWeight.Normal

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        @Composable
        fun CellText(text: String, weight: Float, alignEnd: Boolean = false) {
            Text(
                text = text,
                color = textColor,
                fontWeight = weightFont,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = if (alignEnd) androidx.compose.ui.text.style.TextAlign.End else androidx.compose.ui.text.style.TextAlign.Start,
                modifier = Modifier.weight(weight).padding(end = 4.dp)
            )
        }
        // -----------------------------------

        CellText(col1, weights[0])
        CellText(col2, weights[1])

        if (col3 != null && weights.size > 2) {
            CellText(col3, weights[2])
        }
        if (col4 != null && weights.size > 3) {
            CellText(col4, weights[3], alignEnd = true)
        }
    }
}