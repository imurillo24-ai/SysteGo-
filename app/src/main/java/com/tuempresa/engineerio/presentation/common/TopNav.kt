package com.tuempresa.engineerio.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuempresa.engineerio.core.theme.*

@Composable
fun TopNav(
    viewMode: String,
    xp: Int,
    hearts: Int,
    soundEnabled: Boolean,
    onToggleSound: () -> Unit,
    onCloseLesson: () -> Unit,
    onOpenDrawer: () -> Unit,
    titleOverride: String? = null
) {
    val isLessonView = viewMode == "THEORY" || viewMode == "QUIZ"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark)
            .border(width = 2.dp, color = BorderDark)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Sección Izquierda: Menú o Botón de Salida
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = if (isLessonView) onCloseLesson else onOpenDrawer,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isLessonView) Icons.Default.Close else Icons.Default.Menu,
                    contentDescription = "Acción de navegación",
                    tint = if (isLessonView) TextSecondary else PrimaryCyan
                )
            }

            Text(
                text = titleOverride ?: "SYSTEGO",
                color = Color(0xFFDBFCFF),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // Etiqueta BETA
            Text(
                text = "BETA",
                color = PrimaryCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(PrimaryCyan.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                    .border(1.dp, PrimaryCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        // Sección Derecha: XP y Vidas
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!isLessonView) {
                BadgePill(
                    iconTint = WarningOrange,
                    text = xp.toString()
                )
            }

            BadgePill(
                iconTint = if (hearts > 1) ErrorPink else Color(0xFFFFB4AB),
                text = hearts.toString()
            )
        }
    }
}

@Composable
fun BadgePill(iconTint: Color, text: String) {
    Row(
        modifier = Modifier
            .background(SurfaceLight, RoundedCornerShape(50))
            .border(1.dp, BorderDark, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(iconTint, RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = TextPrimary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp
        )
    }
}
