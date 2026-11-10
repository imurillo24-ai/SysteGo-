package com.tuempresa.engineerio.presentation.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuempresa.engineerio.core.theme.*
import com.tuempresa.engineerio.domain.model.QuizQuestion

@Composable
fun QuizScreen(
    questions: List<QuizQuestion>,
    onCompleteQuiz: (Int) -> Unit,
    onExit: () -> Unit,
    currentHearts: Int,
    onDeductHeart: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var selectedOptionId by remember { mutableStateOf<String?>(null) }
    var isAnswerChecked by remember { mutableStateOf(false) }
    var totalXpEarned by remember { mutableStateOf(0) }
    var showExitDialog by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrElse(currentIndex) { questions.first() }
    val progressPercent = ((currentIndex + 1).toFloat() / questions.size) * 100
    val isCurrentCorrect = selectedOptionId == currentQuestion.correctOptionId

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Barra Superior de Salida y Contador de Vidas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .statusBarsPadding()
                .border(1.dp, BorderDark)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { showExitDialog = true }
            ) {
                IconButton(
                    onClick = { showExitDialog = true },
                    modifier = Modifier
                        .size(36.dp)
                        .background(SurfaceLight, CircleShape)
                        .border(1.dp, BorderDark, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Salir del quiz",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Salir",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }

            // Indicador de Vidas Activas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .background(SurfaceLight, RoundedCornerShape(20.dp))
                    .border(1.dp, BorderDark, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text = "❤️", fontSize = 14.sp)
                Text(
                    text = "$currentHearts",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = ErrorPink
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Barra de Progreso del Cuestionario
            item {
                Column(modifier = Modifier.padding(bottom = 20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "PREGUNTA ${currentIndex + 1} DE ${questions.size}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Text(text = "${progressPercent.toInt()}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(50)),
                        color = SuccessGreen,
                        trackColor = BorderDark
                    )
                }
            }

            // Pregunta
            item {
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Bloque de Código Opcional
            currentQuestion.codeSnippet?.let { snippet ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDark, RoundedCornerShape(12.dp))
                            .border(2.dp, BorderDark, RoundedCornerShape(12.dp))
                            .padding(16.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        Column {
                            snippet.lines.forEach { line ->
                                Text(
                                    text = line.text,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp,
                                    color = when (line.highlight) {
                                        "comment" -> TextSecondary
                                        "string" -> WarningOrange
                                        "blank" -> PrimaryCyan
                                        else -> TextPrimary
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Opciones de Respuesta
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    currentQuestion.options.forEach { option ->
                        val isSelected = selectedOptionId == option.id
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) PrimaryCyan.copy(alpha = 0.15f) else SurfaceDark)
                                .border(2.dp, if (isSelected) PrimaryCyan else BorderDark, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswerChecked) { selectedOptionId = option.id }
                                .padding(16.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(if (isSelected) PrimaryCyan else BackgroundDark, RoundedCornerShape(6.dp))
                                        .border(1.dp, if (isSelected) PrimaryCyan else BorderDark, RoundedCornerShape(6.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = option.label,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (isSelected) SurfaceDark else TextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = option.text,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) PrimaryCyan else TextPrimary,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Panel Inferior: Verificación y Retroalimentación
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(SurfaceDark)
                .navigationBarsPadding()
                .border(1.dp, BorderDark)
                .padding(16.dp)
        ) {
            if (!isAnswerChecked) {
                Button(
                    onClick = {
                        if (selectedOptionId != null) {
                            isAnswerChecked = true
                            if (isCurrentCorrect) {
                                totalXpEarned += currentQuestion.xpReward
                            } else {
                                onDeductHeart()
                            }
                        }
                    },
                    enabled = selectedOptionId != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
                ) {
                    Text(text = "VERIFICAR", color = SurfaceDark, fontWeight = FontWeight.ExtraBold)
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isCurrentCorrect) " ¡RESPUESTA CORRECTA!" else "❌ INCORRECTO",
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isCurrentCorrect) SuccessGreen else ErrorPink,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = currentQuestion.explanation, fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (currentIndex < questions.size - 1) {
                                currentIndex++
                                selectedOptionId = null
                                isAnswerChecked = false
                            } else {
                                onCompleteQuiz(totalXpEarned)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isCurrentCorrect) SuccessGreen else ErrorPink)
                    ) {
                        Text(text = "SIGUIENTE", color = SurfaceDark, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }

    // Modal de Confirmación para Salir de la Lección
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            containerColor = SurfaceGlass,
            title = {
                Text(
                    text = "¿Salir de la lección?",
                    color = TextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "Si sales ahora, perderás tu progreso actual en este cuestionario.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        onExit()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorPink),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Salir", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(text = "Continuar", color = PrimaryCyan, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
}



