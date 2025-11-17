package org.example.cmp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.bebas_neue_regular
import org.jetbrains.compose.resources.Font


private val getBebasFontFamily @Composable get() = FontFamily(
    Font(Res.font.bebas_neue_regular, FontWeight.Normal)
)


val AppTypography @Composable get()  = Typography(
    // The weight property here tells Compose which Font to pick from MyCustomFontFamily
    headlineLarge = TextStyle(
        fontFamily = getBebasFontFamily,
        fontWeight = FontWeight.Bold, // Compose automatically uses myfont_bold.ttf
        fontSize = 32.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = getBebasFontFamily,
        fontWeight = FontWeight.Normal, // Compose automatically uses myfont_regular.ttf
        fontSize = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = getBebasFontFamily,
        fontWeight = FontWeight.SemiBold, // Compose automatically uses myfont_semibold.ttf
        fontSize = 12.sp,
    )
)