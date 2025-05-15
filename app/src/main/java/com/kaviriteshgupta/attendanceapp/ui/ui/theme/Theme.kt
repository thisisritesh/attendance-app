package com.kaviriteshgupta.attendanceapp.ui.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kaviriteshgupta.attendanceapp.R

private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    secondary = bgColor,
    tertiary = primaryColor,
    background = bgColor,
    onBackground = bgColor
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    secondary = bgColor,
    tertiary = primaryColor,
    background = bgColor,
    onBackground = bgColor

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)



private val appTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_medium,
                FontWeight.Medium,
                style = FontStyle.Normal
            )
        )
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_bold,
                FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    headlineSmall = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_bold,
                FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    titleLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_bold,
                FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    displayLarge = TextStyle(
        fontSize = 144.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_bold,
                FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    displayMedium = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_bold,
                FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    ),
    displaySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_semibold,
                FontWeight.SemiBold,
                style = FontStyle.Normal
            )
        )
    ),
    titleMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_semibold,
                FontWeight.SemiBold,
                style = FontStyle.Normal
            )
        )
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_medium,
                FontWeight.Medium,
                style = FontStyle.Normal
            )
        )
    ), bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_semibold,
                FontWeight.SemiBold,
                style = FontStyle.Normal
            )
        )
    ), bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(
            Font(
                R.font.montserrat_bold,
                FontWeight.Bold,
                style = FontStyle.Normal
            )
        )
    )
)

@Composable
fun AttendanceAppTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
        typography = appTypography,
        content = content
    )
}