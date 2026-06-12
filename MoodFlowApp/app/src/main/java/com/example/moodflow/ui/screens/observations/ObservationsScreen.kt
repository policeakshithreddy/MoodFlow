package com.example.moodflow.ui.screens.observations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodflow.theme.ChartDateLabel
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.TealDeep
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.BottomNavBar
import com.example.moodflow.ui.components.ChartData
import com.example.moodflow.ui.components.FlowChart
import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodDot
import com.example.moodflow.ui.components.NavItem

@Composable
fun ObservationsScreen(
    onNavigate: (NavItem) -> Unit
) {
    val chartData = listOf(
        ChartData("May 18", 1),
        ChartData("May 25", 3),
        ChartData("Jun 1", 2),
        ChartData("Jun 8", 4)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground(
            tealAlpha = 0.08f,
            sandAlpha = 0.12f,
            coralAlpha = 0.12f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 28.dp)
                .padding(top = 72.dp, bottom = 110.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Insights",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Ink
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Here is what your flow is showing you.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Muted,
                        modifier = Modifier.fillMaxWidth(0.72f)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Rounded.AutoAwesome,
                        contentDescription = "Highlights",
                        tint = Ink
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column {
                    InsightRow(
                        moodIndex = 1,
                        text = "You felt calmer this week."
                    )
                    InsightRow(
                        moodIndex = 2,
                        text = "Your happiest moments happened in the evening."
                    )
                    InsightRow(
                        moodIndex = 3,
                        text = "Stress levels decreased this month."
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    style = MaterialTheme.typography.labelMedium,
                    color = TealDeep
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            FlowChart(data = chartData)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                chartData.forEach {
                    Text(
                        text = it.dayLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = ChartDateLabel
                    )
                }
            }
        }

        BottomNavBar(
            currentRoute = NavItem.INSIGHTS,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun InsightRow(
    moodIndex: Int,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoodDot(
            moodColors = MoodPalette[moodIndex],
            size = 48.dp
        )
        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = Color(0xFFA0A6AD),
            modifier = Modifier.size(18.dp)
        )
    }
}
