package com.example.moodflow.ui.screens.journey

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodflow.data.MoodEntry
import com.example.moodflow.theme.CoralTagBg
import com.example.moodflow.theme.CoralTagText
import com.example.moodflow.theme.FilterActiveBg
import com.example.moodflow.theme.FilterInactiveText
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MemoryMoreButton
import com.example.moodflow.theme.MemorySectionHeader
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.SandTagBg
import com.example.moodflow.theme.SandTagText
import com.example.moodflow.theme.SurfaceStrong
import com.example.moodflow.theme.TealTagBg
import com.example.moodflow.theme.TealTagText
import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodOrb
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun JourneyHeader(
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Journey",
            style = MaterialTheme.typography.headlineLarge,
            color = Ink
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Journey",
                    tint = Ink
                )
            }
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Filters",
                    tint = Ink
                )
            }
        }
    }
}

@Composable
fun FilterTabs(
    filters: List<JourneyFilter>,
    selectedFilter: JourneyFilter,
    onFilterSelected: (JourneyFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == selectedFilter
            val bgColor = if (isSelected) FilterActiveBg else Color(0xA8FFFFFF)
            val textColor = if (isSelected) Color.White else FilterInactiveText
            val borderColor = if (isSelected) Color.Transparent else Color(0xD1FFFFFF)

            Box(
                modifier = Modifier
                    .height(34.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(bgColor)
                    .border(1.dp, borderColor, RoundedCornerShape(999.dp))
                    .clickable { onFilterSelected(filter) }
                    .padding(horizontal = 13.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = filter.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun JourneySectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MemorySectionHeader,
        modifier = modifier.padding(horizontal = 28.dp, vertical = 20.dp)
    )
}

@Composable
fun MemoryCard(
    entry: MoodEntry,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val moodColors = MoodPalette.getOrNull(entry.moodIndex) ?: MoodPalette[1] // Default Calm
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    val timeString = Instant.ofEpochMilli(entry.timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
        .format(timeFormatter)

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .clickable(onClick = onCardClick)
    ) {
        Row(
            modifier = Modifier.padding(start = 22.dp, top = 18.dp, bottom = 18.dp, end = 18.dp),
            verticalAlignment = Alignment.Top
        ) {
            MemoryGlow(moodLabel = moodColors.label, modifier = Modifier.size(52.dp))
            
            Spacer(modifier = Modifier.width(15.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                // Meta info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = moodColors.label,
                            style = MaterialTheme.typography.titleMedium,
                            color = Ink
                        )
                        Text(
                            text = timeString,
                            fontSize = 11.sp,
                            color = Muted
                        )
                    }
                    IconButton(
                        onClick = { /* More options */ },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreHoriz,
                            contentDescription = "More options",
                            tint = MemoryMoreButton
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(11.dp))
                
                // Reflection (Prioritized)
                if (!entry.reflection.isNullOrBlank()) {
                    Text(
                        text = entry.reflection,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Ink
                    )
                }
                
                // Tags
                if (entry.contexts.isNotBlank()) {
                    val tags = entry.contexts.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    Row(
                        modifier = Modifier.padding(top = 13.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.take(2).forEach { tag ->
                            MemoryTag(text = tag, moodLabel = moodColors.label)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MemoryGlow(
    moodLabel: String,
    modifier: Modifier = Modifier
) {
    // Map mood label to dominant colors
    val (t, s, c) = when (moodLabel) {
        "Reflective" -> Triple(1.3f, 0.7f, 0.5f)
        "Calm" -> Triple(1.1f, 0.9f, 0.6f)
        "Focused" -> Triple(0.6f, 1.3f, 0.6f)
        "Inspired" -> Triple(0.8f, 1.1f, 1.1f)
        "Joyful" -> Triple(0.5f, 0.7f, 1.3f)
        else -> Triple(1f, 1f, 1f)
    }

    Box(modifier = modifier) {
        MoodOrb(
            tealProminence = t,
            sandProminence = s,
            coralProminence = c,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MemoryTag(
    text: String,
    moodLabel: String,
    modifier: Modifier = Modifier
) {
    // Tag styling based on prototype:
    // Joyful/Inspired -> coral-tag
    // Reflective/Calm -> teal-tag
    // Focused -> sand-tag
    
    val (bgColor, textColor) = when (moodLabel) {
        "Joyful", "Inspired" -> CoralTagBg to CoralTagText
        "Focused" -> SandTagBg to SandTagText
        else -> TealTagBg to TealTagText
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(bgColor)
            .padding(horizontal = 9.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold,
            color = textColor
        )
    }
}
