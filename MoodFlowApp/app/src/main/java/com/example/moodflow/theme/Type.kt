package com.example.moodflow.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.moodflow.R

val ManropeFamily = FontFamily(
    Font(R.font.manrope, FontWeight.Normal),
    Font(R.font.manrope, FontWeight.Medium),
    Font(R.font.manrope, FontWeight.SemiBold),
    Font(R.font.manrope, FontWeight.Bold),
    Font(R.font.manrope, FontWeight.ExtraBold)
)

val MoodFlowTypography = Typography(
    // 34sp ExtraBold -0.045em — Onboarding headline
    displayLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
        lineHeight = (34 * 1.12).sp,
        letterSpacing = (-0.045).em
    ),
    // 31sp ExtraBold -0.035em — Selected mood label
    displayMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 31.sp,
        lineHeight = 31.sp,
        letterSpacing = (-0.035).em
    ),
    // 27sp Bold -0.035em — Screen titles (Good Morning, Journey)
    headlineLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 27.sp,
        lineHeight = (27 * 1.05).sp,
        letterSpacing = (-0.035).em
    ),
    // 24sp Bold -0.035em — Question headings
    headlineMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = (24 * 1.2).sp,
        letterSpacing = (-0.035).em
    ),
    // 21sp Bold -0.02em — Canvas copy heading
    headlineSmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp,
        lineHeight = (21 * 1.2).sp,
        letterSpacing = (-0.02).em
    ),
    // 16sp ExtraBold — Button text
    titleLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    // 14sp ExtraBold — Section headings (Recent Journey, Energy level)
    titleMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        lineHeight = (14 * 1.2).sp,
        letterSpacing = 0.sp
    ),
    // 13sp ExtraBold — Chip text, labels
    titleSmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp,
        lineHeight = (13 * 1.45).sp,
        letterSpacing = 0.sp
    ),
    // 15sp Normal — Body text (onboarding subtitle)
    bodyLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = (15 * 1.55).sp,
        letterSpacing = 0.sp
    ),
    // 14sp Normal — Secondary text
    bodyMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = (14 * 1.55).sp,
        letterSpacing = 0.sp
    ),
    // 13sp Normal — Small body (quote, reflection card)
    bodySmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = (13 * 1.45).sp,
        letterSpacing = 0.sp
    ),
    // 12sp ExtraBold — Entry name, small bold
    labelLarge = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
        lineHeight = (12 * 1.3).sp,
        letterSpacing = 0.sp
    ),
    // 11sp ExtraBold — See all, filter tabs, time
    labelMedium = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 11.sp,
        lineHeight = (11 * 1.35).sp,
        letterSpacing = 0.sp
    ),
    // 10sp ExtraBold — Nav labels, tag, mood spectrum labels, chart dates
    labelSmall = TextStyle(
        fontFamily = ManropeFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp,
        lineHeight = (10 * 1.4).sp,
        letterSpacing = 0.sp
    )
)
