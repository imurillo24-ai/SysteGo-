package com.tuempresa.engineerio.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuempresa.engineerio.core.theme.*
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.tuempresa.engineerio.R
import com.tuempresa.engineerio.domain.model.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (UserProfile) -> Unit,
    onGuestLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var selectedAvatar by remember { mutableStateOf("⚡") }
    var selectedRole by remember { mutableStateOf("Arquitecto de Sistemas") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val avatars = listOf("⚡", "💻", "🚀", "🤖", "🛠️", "👾", "🔥", "🔮")
    val roles = listOf(
        "Arquitecto de Sistemas",
        "Ingeniero Backend",
        "Especialista Cloud",
        "Fullstack Cyberpunk",
        "Líder Técnico"
    )

    // Pulsing animation for logo glow
    val infiniteTransition = rememberInfiniteTransition(label = "GlowTransition")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowScale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Background Decorative Neon Elements
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-80).dp)
                .scale(glowScale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            PrimaryCyan.copy(alpha = glowAlpha * 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-80).dp, y = 100.dp)
                .scale(glowScale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            NeonPurple.copy(alpha = glowAlpha * 0.35f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // --- HEADER & MASCOT LOGO ---
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = SurfaceDark,
                modifier = Modifier
                    .size(150.dp)
                    .border(2.dp, PrimaryCyan, RoundedCornerShape(28.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.systego_logo),
                    contentDescription = "Logo Mascot SysteGo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SYSTEGO",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                letterSpacing = 3.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(SuccessGreen, CircleShape)
                )
                Text(
                    text = "NEXUS DE INGENIERÍA V2.5",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryCyan,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- LOGIN FORM CARD ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderGlowing.copy(alpha = 0.5f), RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceGlass)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "REGISTRO DE USUARIO",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Ingresa tus datos para guardar tu progreso e XP",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // 1. NOMBRE COMPLETO
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            errorMessage = null
                        },
                        label = { Text("Nombre Completo", color = TextSecondary) },
                        placeholder = { Text("Ej. Alex Mercer", color = TextSecondary.copy(alpha = 0.5f)) },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryCyan)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryCyan,
                            unfocusedBorderColor = BorderDark,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2. APODO / NICKNAME
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = {
                            nickname = it
                            errorMessage = null
                        },
                        label = { Text("Apodo o Nickname", color = TextSecondary) },
                        placeholder = { Text("Ej. cyber_architect", color = TextSecondary.copy(alpha = 0.5f)) },
                        leadingIcon = {
                            Icon(Icons.Default.AlternateEmail, contentDescription = null, tint = NeonPurple)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonPurple,
                            unfocusedBorderColor = BorderDark,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // 3. SELECCIÓN DE AVATAR
                    Text(
                        text = "ELIGE TU AVATAR CYBER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(avatars) { avatar ->
                            val isSelected = avatar == selectedAvatar
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) PrimaryCyan.copy(alpha = 0.2f) else SurfaceDark)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) PrimaryCyan else BorderDark,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedAvatar = avatar },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = avatar, fontSize = 22.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // 4. SELECCIÓN DE ROL / RANGO
                    Text(
                        text = "ESPECIALIDAD O ROL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(roles) { role ->
                            val isSelected = role == selectedRole
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) NeonPurple.copy(alpha = 0.25f) else SurfaceDark)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) NeonPurple else BorderDark,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable { selectedRole = role }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = role,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) TextPrimary else TextSecondary
                                )
                            }
                        }
                    }

                    // MENSAJE DE ERROR
                    AnimatedVisibility(visible = errorMessage != null) {
                        errorMessage?.let { msg ->
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = msg,
                                color = ErrorPink,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 5. BOTÓN PRINCIPAL
                    Button(
                        onClick = {
                            if (fullName.trim().isEmpty()) {
                                errorMessage = "Por favor, ingresa tu Nombre Completo."
                            } else if (nickname.trim().isEmpty()) {
                                errorMessage = "Por favor, ingresa tu Apodo o Nickname."
                            } else {
                                val cleanNickname = if (nickname.startsWith("@")) nickname else "@${nickname.trim()}"
                                onLoginSuccess(
                                    UserProfile(
                                        fullName = fullName.trim(),
                                        nickname = cleanNickname,
                                        avatarEmoji = selectedAvatar,
                                        titleRole = selectedRole,
                                        level = 1
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = NeonGradientCyanPurple,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "CREAR USUARIO E INICIAR",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = SurfaceDark
                                )
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = SurfaceDark,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 6. MODO INVITADO
                    TextButton(
                        onClick = onGuestLogin
                    ) {
                        Text(
                            text = "Entrar como usuario invitado ⚡",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
