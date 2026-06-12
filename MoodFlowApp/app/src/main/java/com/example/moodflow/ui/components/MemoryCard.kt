package com.example.moodflow.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moodflow.data.MoodEntry
import com.example.moodflow.theme.*
import com.example.moodflow.utils.DateFormatter

@Composable
fun MemoryCard(
    moodEntry: MoodEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val moodColors = MoodPalette.getOrElse(moodEntry.moodIndex) { MoodPalette[0] }

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 20.dp),
            verticalAlignment = Alignment.Top
        ) {
            MoodDot(
                moodColors = moodColors,
                size = 54.dp
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = moodColors.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = Ink
                        )
                        Text(
                            text = DateFormatter.formatJourneyTime(moodEntry.timestamp),
                            style = MaterialTheme.typography.labelSmall,
                            color = JourneyMiniTime
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "More Options",
                        tint = MemoryMoreButton,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (moodEntry.reflection.isNotBlank()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = moodEntry.reflection,
                        style = MaterialTheme.typography.bodySmall,
                        color = Ink,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                val tags = moodEntry.contexts.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                if (tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tags.take(2).forEachIndexed { index, tag ->
                            val bgColor = when (index % 3) {
                                0 -> TealTagBg
                                1 -> SandTagBg
                                else -> CoralTagBg
                            }
                            val textColor = when (index % 3) {
                                0 -> TealTagText
                                1 -> SandTagText
                                else -> CoralTagText
                            }
                            Surface(
                                color = bgColor,
                                shape = PillShape
                            ) {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = textColor,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                        if (tags.size > 2) {
                            Text(
                                text = "+${tags.size - 2}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Muted,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
