package com.cristiancizmar.learnalanguage.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = GreenLight,
    primaryVariant = GreenDark,
    secondary = Secondary
)

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = GreenDark,
    secondary = Secondary
)

@Composable
fun LearnALanguageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}