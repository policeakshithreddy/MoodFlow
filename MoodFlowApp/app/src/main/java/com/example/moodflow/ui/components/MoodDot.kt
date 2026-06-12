package com.example.moodflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moodflow.theme.MoodColors

@Composable
fun MoodDot(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

@Composable
fun MoodDot(
    moodColors: MoodColors,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp
) {
    Canvas(modifier = modifier.size(size)) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.56f),
                    moodColors.a.copy(alpha = 0.94f),
                    moodColors.c.copy(alpha = 0.58f)
                ),
                center = Offset(size.toPx() * 0.38f, size.toPx() * 0.34f),
                radius = size.toPx() * 0.62f
            ),
            radius = size.toPx() / 2f,
            center = Offset(size.toPx() / 2f, size.toPx() / 2f)
        )
    }
}
