package com.supdevinci.picoleurbattleroyaledition.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.supdevinci.picoleurbattleroyaledition.R

val BebasNeue = FontFamily(
    Font(R.font.bebas_neue_regular, FontWeight.Normal)
)

val Nunito = FontFamily(
    Font(R.font.nunito_regular,   FontWeight.Normal),
    Font(R.font.nunito_medium,    FontWeight.Medium),
    Font(R.font.nunito_bold,      FontWeight.Bold),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold),
)

val Typography = Typography(
    // Headlines — Bebas Neue (impact, all-caps feel)
    displayLarge   = TextStyle(fontFamily = BebasNeue, fontSize = 57.sp, letterSpacing = 0.sp),
    displayMedium  = TextStyle(fontFamily = BebasNeue, fontSize = 45.sp, letterSpacing = 0.sp),
    displaySmall   = TextStyle(fontFamily = BebasNeue, fontSize = 36.sp, letterSpacing = 0.sp),
    headlineLarge  = TextStyle(fontFamily = BebasNeue, fontSize = 32.sp, letterSpacing = 0.sp),
    headlineMedium = TextStyle(fontFamily = BebasNeue, fontSize = 28.sp, letterSpacing = 0.sp),
    headlineSmall  = TextStyle(fontFamily = BebasNeue, fontSize = 24.sp, letterSpacing = 0.sp),

    // Titles & Body — Nunito (rounded, readable)
    titleLarge   = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium  = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold,      fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp),
    titleSmall   = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold,      fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),

    bodyLarge    = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal,    fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium   = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal,    fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp),
    bodySmall    = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Normal,    fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp),

    labelLarge   = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Bold,      fontSize = 14.sp, letterSpacing = 0.1.sp),
    labelMedium  = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Medium,    fontSize = 12.sp, letterSpacing = 0.5.sp),
    labelSmall   = TextStyle(fontFamily = Nunito, fontWeight = FontWeight.Medium,    fontSize = 11.sp, letterSpacing = 0.5.sp),
)
