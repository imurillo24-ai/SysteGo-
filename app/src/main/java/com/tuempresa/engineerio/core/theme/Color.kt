package com.tuempresa.engineerio.core.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val BackgroundDark = Color(0xFF080B16)
val SurfaceDark = Color(0xFF10131E)
val SurfaceLight = Color(0xFF1C1F2B)
val SurfaceGlass = Color(0xCC141824)
val PrimaryCyan = Color(0xFF00F0FF)
val SuccessGreen = Color(0xFF00FF94)
val ErrorPink = Color(0xFFFF007A)
val WarningOrange = Color(0xFFFFB95F)
val NeonPurple = Color(0xFFA855F7)
val TextPrimary = Color(0xFFE0E1F2)
val TextSecondary = Color(0xFF849495)
val BorderDark = Color(0xFF313441)
val BorderGlowing = Color(0xFF00F0FF)

val NeonGradientCyanPurple = Brush.horizontalGradient(
    colors = listOf(PrimaryCyan, NeonPurple),
)
