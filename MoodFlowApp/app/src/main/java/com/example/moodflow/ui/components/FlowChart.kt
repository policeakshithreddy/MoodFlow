package com.example.moodflow.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.example.moodflow.theme.*

data class ChartData(val dayLabel: String, val moodIndex: Int)

@Composable
fun FlowChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = MaterialTheme.typography.labelSmall.copy(color = ChartDateLabel)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color.Transparent)
            .padding(horizontal = 4.dp, vertical = 8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val chartHeight = height - 30.dp.toPx()
            
            val maxMoodIndex = 4f

            if (data.isEmpty()) return@Canvas
            
            val pointWidth = width / (data.size.coerceAtLeast(2) - 1)
            
            val points = data.mapIndexed { index, chartData ->
                val x = index * pointWidth
                val y = chartHeight - (chartData.moodIndex / maxMoodIndex) * chartHeight
                Offset(x, y)
            }
            
            // Draw line
            val path = Path().apply {
                points.forEachIndexed { index, point ->
                    if (index == 0) {
                        moveTo(point.x, point.y)
                    } else {
                        // Smooth cubic bezier curve
                        val prevPoint = points[index - 1]
                        val cp1x = prevPoint.x + (point.x - prevPoint.x) / 2f
                        val cp1y = prevPoint.y
                        val cp2x = prevPoint.x + (point.x - prevPoint.x) / 2f
                        val cp2y = point.y
                        cubicTo(cp1x, cp1y, cp2x, cp2y, point.x, point.y)
                    }
                }
            }

            val fillPath = Path().apply {
                addPath(path)
                lineTo(width, chartHeight)
                lineTo(0f, chartHeight)
                close()
            }

            drawPath(
                path = fillPath,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Teal.copy(alpha = 0.70f),
                        Sand.copy(alpha = 0.44f),
                        Coral.copy(alpha = 0.70f)
                    )
                )
            )
            
            drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.82f),
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            
            points.forEachIndexed { index, point ->
                drawCircle(
                    color = Color.White.copy(alpha = 0.84f),
                    radius = 2.8.dp.toPx(),
                    center = point
                )

                val textLayoutResult = textMeasurer.measure(
                    text = data[index].dayLabel,
                    style = labelStyle
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = point.x - (textLayoutResult.size.width / 2f),
                        y = height - textLayoutResult.size.height
                    )
                )
            }
        }
    }
}
