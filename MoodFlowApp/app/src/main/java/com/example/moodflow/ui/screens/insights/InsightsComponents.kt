package com.example.moodflow.ui.screens.insights

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Insights
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moodflow.theme.Coral
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.Sand
import com.example.moodflow.theme.Teal
import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodDot
import com.example.moodflow.ui.components.MoodOrb

@Composable
fun InsightsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Insights",
                style = MaterialTheme.typography.headlineLarge,
                color = Ink
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Here is what your flow is showing you.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
                modifier = Modifier.fillMaxWidth(0.72f)
            )
        }

        IconButton(
            onClick = {},
            modifier = Modifier.semantics { contentDescription = "Insight highlights" }
        ) {
            Icon(
                imageVector = Icons.Rounded.Insights,
                contentDescription = null,
                tint = Ink
            )
        }
    }
}

@Composable
fun KeyDiscoveryCard(heroInsight: String) {
    // Generous whitespace, not a typical dashboard card
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 20.dp)
            .semantics { contentDescription = "Key Discovery: $heroInsight" }
    ) {
        Text(
            text = "Key Discovery",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Muted
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = heroInsight,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
            color = Ink,
            lineHeight = androidx.compose.ui.unit.TextUnit(34f, androidx.compose.ui.unit.TextUnitType.Sp)
        )
    }
}

@Composable
fun ObservationList(observations: List<InsightObservation>) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 10.dp)
            .semantics { contentDescription = "Observation List" }
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            observations.forEachIndexed { index, obs ->
                ObservationRow(obs)
            }
        }
    }
}

@Composable
fun ObservationRow(observation: InsightObservation) {
    val moodColors = MoodPalette.getOrElse(observation.moodIndex) { MoodPalette[0] }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .semantics { contentDescription = "Observation: ${observation.text}" },
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoodDot(moodColors = moodColors, size = 44.dp)
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = observation.text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Icon(
            imageVector = Icons.Rounded.Check, // Placeholder for prototype arrow icon
            contentDescription = null,
            tint = Color(0xFFA0A6AD),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun EmptyInsightsState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 60.dp)
            .semantics { contentDescription = "Insights empty state. Your flow is still taking shape." },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(180.dp),
            contentAlignment = Alignment.Center
        ) {
            MoodOrb(moodColors = MoodPalette[1], modifier = Modifier.size(160.dp))
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Your flow is still taking shape.",
            style = MaterialTheme.typography.headlineSmall,
            color = Ink
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Log a few more moments to discover patterns.",
            style = MaterialTheme.typography.bodyMedium,
            color = Muted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun EmotionalFlowChart(modifier: Modifier = Modifier) {
    val revealProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        revealProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 28.dp)
            .semantics { contentDescription = "Emotional flow chart visualizing your moods." }
    ) {
        // Section Heading
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your emotional flow",
                style = MaterialTheme.typography.titleMedium,
                color = Ink
            )
            Text(
                text = "This month",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Muted
            )
        }

        // Wave Chart Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(178.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val cw = size.width
                val ch = size.height
                val sx = cw / 330f
                val sy = ch / 240f

                // We clip horizontally to create an organic reveal animation
                clipRect(right = cw * revealProgress.value) {
                    scale(scaleX = sx, scaleY = sy, pivot = Offset.Zero) {
                        // Background Path (flowB)
                        val pathB = Path().apply {
                            moveTo(0f, 138f)
                            cubicTo(38f, 92f, 72f, 82f, 108f, 122f)
                            cubicTo(151f, 171f, 182f, 177f, 225f, 129f)
                            cubicTo(266f, 83f, 295f, 83f, 330f, 124f)
                            lineTo(330f, 240f)
                            lineTo(0f, 240f)
                            close()
                        }

                        val brushB = Brush.linearGradient(
                            colors = listOf(Teal.copy(alpha = 0.34f), Coral.copy(alpha = 0.38f)),
                            start = Offset(0f, 0f),
                            end = Offset(330f, 0f)
                        )

                        drawPath(path = pathB, brush = brushB)

                        // Foreground Path (flowA)
                        val pathA = Path().apply {
                            moveTo(0f, 117f)
                            cubicTo(39f, 75f, 70f, 69f, 104f, 105f)
                            cubicTo(142f, 147f, 173f, 155f, 213f, 110f)
                            cubicTo(256f, 61f, 288f, 54f, 330f, 92f)
                            lineTo(330f, 240f)
                            lineTo(0f, 240f)
                            close()
                        }

                        val brushA = Brush.linearGradient(
                            colors = listOf(
                                Teal.copy(alpha = 0.72f),
                                Sand.copy(alpha = 0.54f),
                                Coral.copy(alpha = 0.72f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(330f, 0f)
                        )

                        drawPath(path = pathA, brush = brushA)

                        // Path A top stroke (for highlight)
                        val pathAStroke = Path().apply {
                            moveTo(0f, 117f)
                            cubicTo(39f, 75f, 70f, 69f, 104f, 105f)
                            cubicTo(142f, 147f, 173f, 155f, 213f, 110f)
                            cubicTo(256f, 61f, 288f, 54f, 330f, 92f)
                        }

                        drawPath(
                            path = pathAStroke,
                            color = Color.White.copy(alpha = 0.82f),
                            style = Stroke(width = 2f)
                        )
                        
                        // Data points
                        drawCircle(color = Color.White, radius = 4f, center = Offset(286f, 91f))
                        drawCircle(color = Color.White.copy(alpha = 0.78f), radius = 2.5f, center = Offset(46f, 84f))
                        drawCircle(color = Color.White.copy(alpha = 0.74f), radius = 2.5f, center = Offset(211f, 111f))
                    }
                }
            }
        }

        // Chart Dates
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val dateStyle = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold)
            val dateColor = Color(0xFF77808A)
            Text("May 18", style = dateStyle, color = dateColor)
            Text("May 25", style = dateStyle, color = dateColor)
            Text("Jun 1", style = dateStyle, color = dateColor)
            Text("Jun 8", style = dateStyle, color = dateColor)
        }
    }
}
