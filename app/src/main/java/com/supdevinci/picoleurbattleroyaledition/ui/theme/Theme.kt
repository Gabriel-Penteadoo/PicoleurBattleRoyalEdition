package com.supdevinci.picoleurbattleroyaledition.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary              = CrimsonRed,
    onPrimary            = CrimsonOnPrimary,
    primaryContainer     = CrimsonContainer,
    onPrimaryContainer   = CrimsonOnContainer,

    secondary            = Gold,
    onSecondary          = GoldOnSecondary,
    secondaryContainer   = GoldContainer,
    onSecondaryContainer = GoldOnContainer,

    tertiary             = Teal,
    onTertiary           = TealOnTertiary,
    tertiaryContainer    = TealContainer,
    onTertiaryContainer  = TealOnContainer,

    background           = Navy900,
    onBackground         = OnDark,

    surface              = Navy800,
    onSurface            = OnDark,
    surfaceVariant       = Navy700,
    onSurfaceVariant     = OnDarkMuted,

    error                = ErrorRed,
    onError              = ErrorOnError,
    errorContainer       = ErrorContainer,
)

@Composable
fun PicoleurBattleRoyalEditionTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography  = Typography,
        content     = content
    )
}
