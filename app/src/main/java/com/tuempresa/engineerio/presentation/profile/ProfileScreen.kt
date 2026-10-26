package com.tuempresa.engineerio.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuempresa.engineerio.core.theme.*
import com.tuempresa.engineerio.data.repository.CourseRepository
import com.tuempresa.engineerio.domain.model.UserProfile
import com.tuempresa.engineerio.domain.model.UserStats

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    userStats: UserStats,
    onLogout: () -> Unit,
    onBackToMap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER DEL PERFIL ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, PrimaryCyan.copy(alpha = 0.4f), RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar Grande
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(SurfaceDark)
                        .border(2.dp, PrimaryCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userProfile.avatarEmoji,
                        fontSize = 42.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = userProfile.fullName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = userProfile.nickname,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonPurple,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = PrimaryCyan.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryCyan.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = userProfile.titleRole,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryCyan,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- ESTADÍSTICAS DEL USUARIO ---
        Text(
            text = "ESTADÍSTICAS DE COMANDANTE",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "PUNTOS XP",
                value = "${userStats.xp}",
                icon = Icons.Default.Star,
                tint = WarningOrange
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "RACHA",
                value = "${userStats.streakDays} Días",
                icon = Icons.Default.Whatshot,
                tint = ErrorPink
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- INSIGNIAS Y LOGROS ---
        Text(
            text = "INSIGNIAS OBTENIDAS",
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(CourseRepository.badgesData) { badge ->
                Card(
                    modifier = Modifier
                        .width(160.dp)
                        .border(
                            1.dp,
                            if (badge.unlocked) SuccessGreen.copy(alpha = 0.5f) else BorderDark,
                            RoundedCornerShape(16.dp)
                        ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (badge.unlocked) SurfaceDark else SurfaceLight
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = null,
                            tint = if (badge.unlocked) SuccessGreen else TextSecondary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = badge.title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = badge.description,
                            fontSize = 10.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // --- BOTONES DE ACCIÓN ---
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ErrorPink.copy(alpha = 0.7f)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorPink)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, modifier = Modifier.size(18.dp))
                Text("CERRAR SESIÓN / CAMBIAR DE USUARIO", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBackToMap,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryCyan)
        ) {
            Text("VOLVER AL MAPA DE APRENDIZAJE", fontWeight = FontWeight.ExtraBold, color = SurfaceDark, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color
) {
    Card(
        modifier = modifier.border(1.dp, BorderDark, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            }
            Column {
                Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.Black, color = TextPrimary)
            }
        }
    }
}
