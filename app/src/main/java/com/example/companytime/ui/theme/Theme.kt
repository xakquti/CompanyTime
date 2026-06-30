package com.example.companytime.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CornFlowerBlue,
    onPrimary = White,
    background = DarkGrey,
    onBackground = White,
    secondary = CornFlowerBlue,
    tertiary = LightPurple,
    onTertiary = Black,
    onSurfaceVariant = LightGrey,
    surface = Grey,
    onSurface = White,
    error = Rose,
    surfaceDim = White,
    surfaceBright = CornFlowerBlue,
    surfaceVariant = GreyLight,
    surfaceTint = Selected
)

private val LightColorScheme = lightColorScheme(
    primary = CornFlowerBlue,
    onPrimary = White,
    background = Lilac,
    onBackground = Black,
    surface = White,
    secondary = Purple,
    tertiary = LightPurple,
    onTertiary = Black,
    onSurfaceVariant = SoftGrey,
    error = Rose,
    surfaceDim = Black,
    surfaceBright = CornFlowerBlue,
    surfaceVariant = White,
    surfaceTint = Selected
)

@Composable
fun CompanyTimeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}