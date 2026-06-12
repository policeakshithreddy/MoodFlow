package com.example.moodflow.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.moodflow.theme.Coral
import com.example.moodflow.theme.Cream
import com.example.moodflow.theme.Sand
import com.example.moodflow.theme.Teal

@Composable
fun AmbientBackground(
    modifier: Modifier = Modifier,
    baseTop: Color = Color(0xFFFFFDF9),
    baseBottom: Color = Cream,
    tealAlpha: Float = 0.16f,
    sandAlpha: Float = 0.14f,
    coralAlpha: Float = 0.14f
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(baseTop, baseBottom)
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Teal.copy(alpha = tealAlpha), Color.Transparent),
                center = Offset(size.width * 0.20f, size.height * 0.16f),
                radius = size.maxDimension * 0.44f
            ),
            radius = size.maxDimension,
            center = Offset(size.width * 0.20f, size.height * 0.16f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Sand.copy(alpha = sandAlpha), Color.Transparent),
                center = Offset(size.width * 0.82f, size.height * 0.18f),
                radius = size.maxDimension * 0.36f
            ),
            radius = size.maxDimension,
            center = Offset(size.width * 0.82f, size.height * 0.18f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Coral.copy(alpha = coralAlpha), Color.Transparent),
                center = Offset(size.width * 0.82f, size.height * 0.72f),
                radius = size.maxDimension * 0.44f
            ),
            radius = size.maxDimension,
            center = Offset(size.width * 0.82f, size.height * 0.72f)
        )
    }
}
