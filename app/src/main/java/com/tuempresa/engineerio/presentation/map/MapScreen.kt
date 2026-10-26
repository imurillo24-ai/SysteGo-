package com.tuempresa.engineerio.presentation.map

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuempresa.engineerio.core.theme.*
import com.tuempresa.engineerio.domain.model.CourseUnit
import com.tuempresa.engineerio.domain.model.LessonNode
import com.tuempresa.engineerio.domain.model.NodeStatus
import com.tuempresa.engineerio.domain.model.NodeType

@Composable
fun MapScreen(
    activeUnit: CourseUnit,
    nodes: List<LessonNode>,
    onStartTheory: (String) -> Unit,
    onStartQuiz: (String) -> Unit,
    onSelectUnit: (String) -> Unit,
    availableUnits: List<CourseUnit>
) {
    var selectedNode by remember { mutableStateOf<LessonNode?>(null) }
    var showUnitSelector by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "NodePulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera de la Unidad Actual
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                        .border(1.dp, PrimaryCyan.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(PrimaryCyan, CircleShape)
                                )
                                Text(
                                    text = "SECCIÓN ${activeUnit.unitNumber}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PrimaryCyan,
                                    letterSpacing = 1.sp
                                )
                            }
                            TextButton(onClick = { showUnitSelector = !showUnitSelector }) {
                                Text(
                                    text = if (showUnitSelector) "Ocultar" else "Cambiar Unidad",
                                    fontSize = 12.sp,
                                    color = NeonPurple,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = activeUnit.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = activeUnit.description,
                            fontSize = 13.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Barra de Progreso Líquida
                        LinearProgressIndicator(
                            progress = { activeUnit.progressPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(50)),
                            color = SuccessGreen,
                            trackColor = BorderDark,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "PROGRESO DEL CAPÍTULO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextSecondary
                            )
                            Text(
                                text = "${activeUnit.progressPercent}% COMPLETADO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = SuccessGreen
                            )
                        }

                        // Selector Desplegable de Unidades
                        if (showUnitSelector) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                availableUnits.forEach { unit ->
                                    val isSelected = unit.id == activeUnit.id
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (isSelected) PrimaryCyan.copy(alpha = 0.15f) else SurfaceDark)
                                            .border(
                                                1.dp,
                                                if (isSelected) PrimaryCyan else BorderDark,
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable {
                                                onSelectUnit(unit.id)
                                                showUnitSelector = false
                                            }
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = unit.title,
                                                    fontWeight = FontWeight.Bold,
                                                    color = TextPrimary,
                                                    fontSize = 14.sp
                                                )
                                                if (isSelected) {
                                                    Text(
                                                        text = "ACTIVO",
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = PrimaryCyan,
                                                        fontSize = 10.sp
                                                    )
                                                }
                                            }
                                            Text(
                                                text = unit.description,
                                                fontSize = 11.sp,
                                                color = TextSecondary,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Ruta de Nodos Interactivos (Zig-Zag con más detalles visuales)
            items(nodes) { node ->
                Spacer(modifier = Modifier.height(28.dp))

                val isCurrent = node.status == NodeStatus.CURRENT
                val isCompleted = node.status == NodeStatus.COMPLETED
                val isLocked = node.status == NodeStatus.LOCKED

                val nodeBgColor = when {
                    isCompleted -> SuccessGreen
                    isCurrent -> PrimaryCyan
                    else -> SurfaceLight
                }

                val nodeBorderColor = when {
                    isCompleted -> SuccessGreen
                    isCurrent -> PrimaryCyan
                    else -> BorderDark
                }

                Column(
                    modifier = Modifier.offset(x = node.xOffset.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .scale(if (isCurrent) pulseScale else 1f)
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(nodeBgColor.copy(alpha = if (isLocked) 0.3f else 0.2f))
                            .border(
                                width = if (isCurrent) 3.dp else 2.dp,
                                color = nodeBorderColor,
                                shape = CircleShape
                            )
                            .clickable { selectedNode = node },
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            modifier = Modifier.size(68.dp),
                            shape = CircleShape,
                            color = if (isLocked) SurfaceDark else nodeBgColor
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                when {
                                    isCompleted -> Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        tint = SurfaceDark,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    isLocked -> Icon(
                                        Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = TextSecondary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    else -> Icon(
                                        when (node.type) {
                                            NodeType.THEORY -> Icons.Default.Book
                                            NodeType.QUIZ -> Icons.Default.Psychology
                                            NodeType.CHEST -> Icons.Default.CardGiftcard
                                        },
                                        contentDescription = null,
                                        tint = SurfaceDark,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = node.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isLocked) TextSecondary else TextPrimary
                    )

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isCurrent) PrimaryCyan.copy(alpha = 0.2f) else SurfaceDark,
                        border = BorderStroke(1.dp, if (isCurrent) PrimaryCyan else BorderDark),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = when {
                                isCompleted -> "COMPLETADO"
                                isCurrent -> "¡AQUÍ Y AHORA!"
                                else -> "BLOQUEADO"
                            },
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = when {
                                isCompleted -> SuccessGreen
                                isCurrent -> PrimaryCyan
                                else -> TextSecondary
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }

    // Modal / Diálogo al hacer clic en un nodo
    selectedNode?.let { node ->
        AlertDialog(
            onDismissRequest = { selectedNode = null },
            containerColor = SurfaceGlass,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(PrimaryCyan.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (node.type) {
                                NodeType.THEORY -> Icons.Default.Book
                                NodeType.QUIZ -> Icons.Default.Psychology
                                NodeType.CHEST -> Icons.Default.CardGiftcard
                            },
                            contentDescription = null,
                            tint = PrimaryCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = node.title,
                        color = TextPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }
            },
            text = {
                Column {
                    Text(text = node.subtitle, color = TextSecondary, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceDark,
                        border = BorderStroke(1.dp, BorderDark),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("RECOMPENSA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text("+${node.xpReward} XP ⚡", fontSize = 13.sp, fontWeight = FontWeight.Black, color = WarningOrange)
                        }
                    }
                }
            },
            confirmButton = {
                if (node.status != NodeStatus.LOCKED) {
                    Button(
                        onClick = {
                            val id = node.id
                            selectedNode = null
                            if (node.type == NodeType.THEORY) {
                                onStartTheory(id)
                            } else {
                                onStartQuiz(id)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = SurfaceDark)
                            Text(text = "INICIAR LECCIÓN", color = SurfaceDark, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedNode = null }) {
                    Text(text = "Cerrar", color = TextSecondary)
                }
            }
        )
    }
}
