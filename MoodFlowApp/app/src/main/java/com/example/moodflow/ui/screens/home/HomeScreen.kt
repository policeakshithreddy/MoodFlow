package com.example.moodflow.ui.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodflow.data.MoodEntry
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.JourneyMiniTime
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.QuoteAuthor
import com.example.moodflow.theme.QuoteMark
import com.example.moodflow.theme.QuoteText
import com.example.moodflow.theme.TealDeep
import com.example.moodflow.theme.Cream
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.BottomNavBar
import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodDot
import com.example.moodflow.ui.components.MoodOrb
import com.example.moodflow.ui.components.NavItem
import com.example.moodflow.ui.components.PrimaryButton
import com.example.moodflow.utils.DateFormatter

@Composable
fun HomeScreen(
    onNavigateToLog: () -> Unit,
    onNavigateTo: (NavItem) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val recentEntries by viewModel.recentEntries.collectAsState()
    val displayEntries = recentEntries.ifEmpty { sampleHomeEntries() }.take(2)

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp) // padding for floating bottom nav
        ) {
            HomeHeader()
            
            QuoteOfTheDay(
                quote = "Calm is not something you find. It is something you allow.",
                author = "Unknown",
                onNavigateToLog = onNavigateToLog,
                onNavigateTo = onNavigateTo,
                displayEntries = displayEntries
            )
        }
        
        BottomNavBar(
            currentRoute = NavItem.HOME,
            onNavigate = onNavigateTo,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "Good Morning",
                style = MaterialTheme.typography.headlineLarge,
                color = Ink
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Take a deep breath and be with yourself.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
                modifier = Modifier.fillMaxWidth(0.72f)
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Rounded.NotificationsNone,
                contentDescription = "Notifications",
                tint = Ink
            )
        }
    }
}

@Composable
private fun QuoteOfTheDay(
    quote: String,
    author: String,
    onNavigateToLog: () -> Unit,
    onNavigateTo: (NavItem) -> Unit,
    displayEntries: List<MoodEntry>
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "\"",
                style = MaterialTheme.typography.displayMedium,
                color = QuoteMark,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column {
                Text(
                    text = quote,
                    style = MaterialTheme.typography.bodySmall,
                    color = QuoteText
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(258.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MoodOrb(
                        moodColors = MoodPalette[1],
                        modifier = Modifier.size(252.dp)
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Calm",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "You feel balanced\nand at ease.",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(alpha = 0.92f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "\"",
                            style = MaterialTheme.typography.displayMedium,
                            color = QuoteMark,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column {
                            Text(
                                text = "Calm is not something you find. It is something you allow.",
                                style = MaterialTheme.typography.bodySmall,
                                color = QuoteText
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Unknown",
                                style = MaterialTheme.typography.labelSmall,
                                color = QuoteAuthor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PrimaryButton(
                        text = "Log Mood",
                        onClick = onNavigateToLog,
                        modifier = Modifier.weight(1f)
                    )
                    MoodDot(
                        moodColors = MoodPalette[3],
                        size = 58.dp,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Cream)
                            .padding(3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Journey",
                        style = MaterialTheme.typography.titleMedium,
                        color = Ink
                    )
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.labelMedium,
                        color = TealDeep
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                displayEntries.forEach { entry ->
                    RecentJourneyRow(entry = entry)
                }
            }
        }
    }
}

@Composable
private fun RecentJourneyRow(
    entry: MoodEntry
) {
    val moodColors = MoodPalette.getOrElse(entry.moodIndex) { MoodPalette[0] }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoodDot(moodColors = moodColors, size = 38.dp)
        Spacer(modifier = Modifier.size(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = moodColors.label,
                style = MaterialTheme.typography.labelLarge,
                color = Ink
            )
            Text(
                text = DateFormatter.formatJourneyTime(entry.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = JourneyMiniTime
            )
        }
        Text(
            text = entry.reflection.ifBlank { moodColors.description },
            style = MaterialTheme.typography.labelSmall,
            color = Ink,
            modifier = Modifier.weight(1.15f)
        )
    }
}

private fun sampleHomeEntries(): List<MoodEntry> {
    val now = System.currentTimeMillis()
    return listOf(
        MoodEntry(
            id = 1,
            timestamp = now,
            moodIndex = 3,
            energyLevel = 3,
            contexts = "Creative",
            reflection = "Worked on MoodFlow design"
        ),
        MoodEntry(
            id = 2,
            timestamp = now - 86_400_000L,
            moodIndex = 1,
            energyLevel = 2,
            contexts = "Rest",
            reflection = "Evening walk after dinner"
        )
    )
}
