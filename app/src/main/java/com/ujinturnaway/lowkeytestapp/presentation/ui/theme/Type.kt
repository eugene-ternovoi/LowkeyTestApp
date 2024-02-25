package com.ujinturnaway.lowkeytestapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ujinturnaway.lowkeytestapp.R

// Set of Material typography styles to start with

val mainFontFamily = FontFamily(
    Font(R.font.golos_text_regular, weight = FontWeight.Light),
    Font(R.font.golos_text_regular, weight = FontWeight.Normal),
    Font(R.font.golos_text_medium, weight = FontWeight.Medium),
    Font(R.font.golos_text_demibold, weight = FontWeight.SemiBold),
    Font(R.font.golos_text_bold, weight = FontWeight.Bold)
)

val primary = TextStyle(
    fontFamily = mainFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
)

val Typography = Typography(
    bodyLarge = primary
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)