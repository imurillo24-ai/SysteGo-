package com.tuempresa.engineerio.presentation.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                    border = BorderStroke(2.dp, BorderDark)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CAPÍTULO ACTUAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PrimaryCyan
                            )
                            TextButton(onClick = { showUnitSelector = !showUnitSelector }) {
                                Text(text = "Cambiar Unidad", fontSize = 12.sp, color = TextSecondary)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = activeUnit.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = activeUnit.description,
                            fontSize = 13.sp,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
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

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "PROGRESO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text(
                                text = "${activeUnit.progressPercent}% COMPLETADO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }

                        // Selector Desplegable de Unidades
                        if (showUnitSelector) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                availableUnits.forEach { unit ->
                                    val isSelected = unit.id == activeUnit.id
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isSelected) PrimaryCyan.copy(alpha = 0.1f) else SurfaceDark)
                                            .border(1.dp, if (isSelected) PrimaryCyan else BorderDark, RoundedCornerShape(10.dp))
                                            .clickable {
                                                onSelectUnit(unit.id)
                                                showUnitSelector = false
                                            }
                                            .padding(12.dp)
                                    ) {
                                        Column {
                                            Text(text = unit.title, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                                            Text(text = unit.description, fontSize = 11.sp, color = TextSecondary, maxLines = 1)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Ruta de Nodos Interactivos (Zig-Zag)
            items(nodes) { node ->
                Spacer(modifier = Modifier.height(24.dp))

                // Desplazamiento lateral simulando el camino en zigzag según xOffset
                Box(
                    modifier = Modifier
                        .offset(x = node.xOffset.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            when (node.status) {
                                NodeStatus.COMPLETED -> SuccessGreen
                                NodeStatus.CURRENT -> PrimaryCyan
                                NodeStatus.LOCKED -> SurfaceLight
                            }
                        )
                        .border(2.dp, BorderDark, CircleShape)
                        .clickable { selectedNode = node },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (node.type) {
                            NodeType.THEORY -> " Teoría"
                            NodeType.QUIZ -> " Quiz"
                            NodeType.CHEST -> " Cofre"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (node.status == NodeStatus.LOCKED) TextSecondary else SurfaceDark
                    )
                }
            }
        }
    }

    // Modal / Diálogo al hacer clic en un nodo
    selectedNode?.let { node ->
        AlertDialog(
            onDismissRequest = { selectedNode = null },
            containerColor = SurfaceDark,
            title = {
                Text(text = node.title, color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(text = node.subtitle, color = TextSecondary, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Recompensa: +${node.xpReward} XP", color = PrimaryCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
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
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
                    ) {
                        Text(text = "INICIAR", color = SurfaceDark, fontWeight = FontWeight.Bold)
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
