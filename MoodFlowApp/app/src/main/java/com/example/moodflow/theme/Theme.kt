package com.example.moodflow.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MoodFlowLightColorScheme = lightColorScheme(
    primary = TealDeep,
    onPrimary = Color.White,
    primaryContainer = TealSoft,
    onPrimaryContainer = TealDeep,
    secondary = Sand,
    onSecondary = Ink,
    secondaryContainer = SandSoft,
    onSecondaryContainer = SandTagText,
    tertiary = Coral,
    onTertiary = Color.White,
    tertiaryContainer = CoralSoft,
    onTertiaryContainer = CoralTagText,
    background = Cream,
    onBackground = Ink,
    surface = Cream,
    onSurface = Ink,
    surfaceVariant = Cream2,
    onSurfaceVariant = Muted,
    outline = Line,
    outlineVariant = Line
)

private val MoodFlowDarkColorScheme = darkColorScheme(
    primary = Teal,
    onPrimary = Ink,
    primaryContainer = TealDeep,
    onPrimaryContainer = TealSoft,
    secondary = Sand,
    onSecondary = Ink,
    secondaryContainer = Color(0xFF3D3220),
    onSecondaryContainer = SandSoft,
    tertiary = Coral,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF3D2420),
    onTertiaryContainer = CoralSoft,
    background = Color(0xFF0F1318),
    onBackground = Color(0xFFE8E4DF),
    surface = Color(0xFF0F1318),
    onSurface = Color(0xFFE8E4DF),
    surfaceVariant = Color(0xFF1A1F26),
    onSurfaceVariant = Color(0xFF9DA3AB),
    outline = Color(0xFF2A3040),
    outlineVariant = Color(0xFF1E242E)
)

@Composable
fun MoodFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) MoodFlowDarkColorScheme else MoodFlowLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MoodFlowTypography,
        shapes = MoodFlowShapes,
        content = content
    )
}
