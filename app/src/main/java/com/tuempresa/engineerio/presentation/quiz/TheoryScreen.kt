package com.tuempresa.engineerio.presentation.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.tuempresa.engineerio.domain.model.TheoryModule

@Composable
fun TheoryScreen(
    module: TheoryModule,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    var selectedConceptId by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Barra de Progreso del Módulo
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "PROGRESO DEL MÓDULO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Text(text = "${module.progressPercent}%", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryCyan)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { module.progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(50)),
                        color = PrimaryCyan,
                        trackColor = BorderDark
                    )
                }
            }

            // Tarjeta Principal de Teoría
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    border = BorderStroke(2.dp, BorderDark)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(PrimaryCyan.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                    .border(1.dp, PrimaryCyan.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = module.moduleType, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryCyan)
                            }
                            Text(text = "⏱️ ${module.duration}", fontSize = 12.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = module.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = module.summary,
                            fontSize = 15.sp,
                            color = TextSecondary,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Principios Clave (Píldoras interactivas)
                        Text(text = "PRINCIPIOS CLAVE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            module.keyConcepts.forEach { concept ->
                                val isSelected = selectedConceptId == concept.id
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { selectedConceptId = if (isSelected) null else concept.id },
                                    color = SurfaceLight,
                                    border = BorderStroke(2.dp, if (isSelected) PrimaryCyan else BorderDark)
                                ) {
                                    Text(
                                        text = concept.name,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) PrimaryCyan else TextPrimary
                                    )
                                }
                            }
                        }

                        // Acordeón de Explicación del Concepto
                        selectedConceptId?.let { conceptId ->
                            val explanation = module.keyConcepts.find { it.id == conceptId }?.explanation
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SurfaceLight, RoundedCornerShape(10.dp))
                                    .border(1.dp, PrimaryCyan.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "💡 $explanation",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Puntos Arquitectónicos
                        Text(text = "CONCLUSIONES ARQUITECTÓNICAS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Spacer(modifier = Modifier.height(8.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            module.architecturePoints.forEach { point ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(BackgroundDark, RoundedCornerShape(10.dp))
                                        .border(1.dp, BorderDark, RoundedCornerShape(10.dp))
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(text = point.title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = point.detail, fontSize = 11.sp, color = TextSecondary)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Botón Inferior Fijo de Continuar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(SurfaceDark)
                .border(1.dp, BorderDark)
                .padding(16.dp)
        ) {
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
            ) {
                Text(text = "CONTINUAR AL QUIZ", color = SurfaceDark, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
