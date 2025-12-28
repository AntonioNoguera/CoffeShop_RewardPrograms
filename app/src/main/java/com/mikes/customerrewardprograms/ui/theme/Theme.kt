package com.mikes.customerrewardprograms.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.caferewards.ui.theme.*

private val LightColorScheme = lightColorScheme(
    // Primary - Tonos de café/espresso
    primary = Coffee600,
    onPrimary = Color.White,
    primaryContainer = Coffee100,
    onPrimaryContainer = Coffee900,

    // Secondary - Tonos mocha/chocolate
    secondary = Mocha500,
    onSecondary = Color.White,
    secondaryContainer = Mocha100,
    onSecondaryContainer = Mocha900,

    // Tertiary - Tonos caramelo/dorado
    tertiary = Caramel500,
    onTertiary = Coffee900,
    tertiaryContainer = Caramel100,
    onTertiaryContainer = Caramel900,

    // Error
    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = ErrorDark,

    // Background & Surface
    background = Coffee50,
    onBackground = Coffee900,
    surface = Color.White,
    onSurface = Coffee900,
    surfaceVariant = Coffee100,
    onSurfaceVariant = Coffee700,

    // Outline & other
    outline = Coffee300,
    outlineVariant = Coffee200,
    scrim = Coffee900.copy(alpha = 0.32f),
    inverseSurface = Coffee800,
    inverseOnSurface = Coffee50,
    inversePrimary = Coffee300,

    // Surface tints
    surfaceTint = Coffee600
)

private val DarkColorScheme = darkColorScheme(
    // Primary - Tonos más claros para contraste en dark mode
    primary = Coffee400,
    onPrimary = Coffee900,
    primaryContainer = Coffee700,
    onPrimaryContainer = Coffee100,

    // Secondary
    secondary = Mocha400,
    onSecondary = Mocha900,
    secondaryContainer = Mocha700,
    onSecondaryContainer = Mocha100,

    // Tertiary
    tertiary = Caramel400,
    onTertiary = Caramel900,
    tertiaryContainer = Caramel700,
    onTertiaryContainer = Caramel100,

    // Error
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Background & Surface
    background = Coffee900,
    onBackground = Coffee100,
    surface = Coffee800,
    onSurface = Coffee100,
    surfaceVariant = Coffee700,
    onSurfaceVariant = Coffee200,

    // Outline & other
    outline = Coffee500,
    outlineVariant = Coffee700,
    scrim = Color.Black.copy(alpha = 0.32f),
    inverseSurface = Coffee100,
    inverseOnSurface = Coffee900,
    inversePrimary = Coffee600,

    // Surface tints
    surfaceTint = Coffee400
)

@Composable
fun CustomerRewardProgramsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color está disponible en Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Soporte para dynamic color:
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        //     val context = LocalContext.current
        //     if (darkTheme) dynamicDarkColorScheme(context)
        //     else dynamicLightColorScheme(context)
        // }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // Status bar transparente
            window.statusBarColor = Color.Transparent.toArgb()

            // Navigation bar del MISMO color que el fondo
            window.navigationBarColor = colorScheme.background.toArgb()

            // Configurar iconos claros/oscuros según el tema
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CafeRewardsTypography,
        content = content
    )
}

/**
 * Helper para previsualizar componentes con el tema
 */
@Composable
fun CafeRewardsPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    CustomerRewardProgramsTheme(darkTheme = darkTheme) {
        content()
    }
}

