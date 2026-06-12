package com.example.moodflow.theme

import androidx.compose.ui.graphics.Color

// ── Primary Palette (from logo) ──
val Teal = Color(0xFF50C6C4)
val TealDeep = Color(0xFF2F9DA1)
val TealSoft = Color(0xFFC9EFEB)

val Sand = Color(0xFFF3C779)
val SandSoft = Color(0xFFFFF0CF)

val Coral = Color(0xFFF38B78)
val CoralSoft = Color(0xFFFFD9D2)

// ── Background / Surface ──
val Cream = Color(0xFFFCF7EF)
val Cream2 = Color(0xFFF6EDE2)
val Surface = Color(0xAEFFFFFF)      // white @ 68%
val SurfaceStrong = Color(0xE0FFFFFF) // white @ 88%

// ── Text ──
val Ink = Color(0xFF182435)
val Muted = Color(0xFF6D7683)

// ── Divider ──
val Line = Color(0x1A3F4C58)          // #3F4C58 @ 10%

// ── Tag Colors ──
val CoralTagText = Color(0xFFC95949)
val CoralTagBg = Color(0xFFFFE2DC)
val TealTagText = Color(0xFF247A7D)
val TealTagBg = Color(0xFFD8F3EF)
val SandTagText = Color(0xFF8A5D08)
val SandTagBg = Color(0xFFFFECBF)

// ── Chip Colors ──
val ChipText = Color(0xFF46515E)
val ChipSelectedText = Color(0xFF236D71)
val ChipSelectedBg = Color(0xC2D5F4F0)  // rgba(213,244,240,.76)

// ── Energy Selected ──
val EnergySelectedText = Color(0xFF6A4D13)
val EnergySelectedBg = Color(0xFFFFE5B8)

// ── Mood Logging Screen Gradients ──
data class MoodColors(
    val label: String,
    val description: String,
    val a: Color,
    val b: Color,
    val c: Color
)

val MoodPalette = listOf(
    MoodColors(
        label = "Reflective",
        description = "Quiet · Aware · Open",
        a = Color(0xFF6FB1C4),
        b = Color(0xFFD7D3BF),
        c = Color(0xFFA9C8BC)
    ),
    MoodColors(
        label = "Calm",
        description = "Balanced · Soft · At ease",
        a = Color(0xFF50C6C4),
        b = Color(0xFFCCEBDC),
        c = Color(0xFFF3C779)
    ),
    MoodColors(
        label = "Focused",
        description = "Clear · Grounded · Present",
        a = Color(0xFF84C9A4),
        b = Color(0xFFF3C779),
        c = Color(0xFFF0AD75)
    ),
    MoodColors(
        label = "Inspired",
        description = "Creative · Energized · Optimistic",
        a = Color(0xFF50C6C4),
        b = Color(0xFFF3C779),
        c = Color(0xFFF38B78)
    ),
    MoodColors(
        label = "Joyful",
        description = "Bright · Connected · Light",
        a = Color(0xFFF6C06A),
        b = Color(0xFFF38B78),
        c = Color(0xFFF6A7B9)
    )
)

// ── Filter tab colors ──
val FilterInactiveText = Color(0xFF5F6874)
val FilterActiveBg = TealDeep
val FilterActiveText = Color.White

// ── Observation / Insight ──
val InsightChevron = Color(0xFFA0A6AD)

// ── Chart date label ──
val ChartDateLabel = Color(0xFF77808A)

// ── Bottom nav ──
val NavInactive = Color(0x59182435) // rgba(24,36,53,.35)
val NavActive = TealDeep

// ── Memory card more button ──
val MemoryMoreButton = Color(0xFF8C939A)

// ── Reflection input ──
val ReflectionLabelColor = Color(0xFF485361)
val ReflectionOptionalColor = Color(0xFF949AA3)
val ReflectionPlaceholder = Color(0xFF929AA3)

// ── Quote mark ──
val QuoteMark = Color(0x47182435) // rgba(24,36,53,.28)
val QuoteText = Color(0xFF55606D)
val QuoteAuthor = Color(0xFF8A929B)

// ── Memory list section header ──
val MemorySectionHeader = Color(0xFF4F5965)
val JourneyMiniTime = Color(0xFF7E8790)
