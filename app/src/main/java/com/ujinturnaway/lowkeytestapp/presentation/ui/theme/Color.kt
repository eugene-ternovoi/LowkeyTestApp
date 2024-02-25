package com.ujinturnaway.lowkeytestapp.presentation.ui.theme

import androidx.compose.ui.graphics.Color

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val PrimaryColorLight = Color(0xFF363B64)
val primaryColorDark = Color(0xFFD9D9DA)
val backgroundColorLight = Color(0xFFE5E5E5)
val backgroundColorDark = Color(0xFF181818)
val secondaryColorDark = Color(0xFF202020)
val secondaryColorLight = Color.White
val tertiaryColorLight = Color(0xFFA098AE)
val tertiaryColorDark = Color(0xFF727272)

val DarkColorScheme = darkColorScheme(
    primary = primaryColorDark,
    secondary = secondaryColorDark,
    background = backgroundColorDark,
    tertiary = tertiaryColorDark,
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryColorLight,
    secondary = secondaryColorLight,
    background = backgroundColorLight,
    tertiary = tertiaryColorLight,
)