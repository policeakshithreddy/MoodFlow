package com.example.moodflow.ui.screens.mooddetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moodflow.data.MoodEntry
import com.example.moodflow.theme.EnergySelectedBg
import com.example.moodflow.theme.EnergySelectedText
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.PillShape
import com.example.moodflow.theme.SandTagBg
import com.example.moodflow.theme.SandTagText
import com.example.moodflow.theme.TealTagBg
import com.example.moodflow.theme.TealTagText
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodOrb
import com.example.moodflow.utils.DateFormatter

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun MoodDetailsScreen(
    moodEntry: MoodEntry,
    onBack: () -> Unit
) {
    val moodColors = MoodPalette.getOrElse(moodEntry.moodIndex) { MoodPalette[0] }
    val energyLabel = when (moodEntry.energyLevel) {
        1 -> "Low"
        2 -> "Medium"
        3 -> "High"
        else -> "Medium"
    }
    val tags = moodEntry.contexts.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground(
            tealAlpha = 0.10f,
            sandAlpha = 0.12f,
            coralAlpha = 0.14f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 28.dp)
                .padding(top = 10.dp, bottom = 28.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Ink
                    )
                }
                MoodOrb(
                    moodColors = MoodPalette[moodEntry.moodIndex],
                    modifier = Modifier.size(140.dp)
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Ink
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = moodColors.label,
                style = MaterialTheme.typography.displayMedium,
                color = Ink
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = DateFormatter.formatJourneyTime(moodEntry.timestamp),
                style = MaterialTheme.typography.labelLarge,
                color = Muted
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = moodColors.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Muted
            )

            Spacer(modifier = Modifier.height(30.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(22.dp)) {
                    Text(
                        text = "Reflection",
                        style = MaterialTheme.typography.titleMedium,
                        color = Ink
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = moodEntry.reflection.ifBlank { "A quiet moment of emotional awareness." },
                        style = MaterialTheme.typography.bodyLarge,
                        color = Ink
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    Text(
                        text = "Energy level",
                        style = MaterialTheme.typography.titleMedium,
                        color = Ink
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = EnergySelectedBg,
                        shape = PillShape
                    ) {
                        Text(
                            text = energyLabel,
                            style = MaterialTheme.typography.labelLarge,
                            color = EnergySelectedText,
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp)
                        )
                    }

                    if (tags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(26.dp))
                        Text(
                            text = "Influences",
                            style = MaterialTheme.typography.titleMedium,
                            color = Ink
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(9.dp),
                            verticalArrangement = Arrangement.spacedBy(9.dp)
                        ) {
                            tags.forEachIndexed { index, tag ->
                                Surface(
                                    color = if (index % 2 == 0) TealTagBg else SandTagBg,
                                    shape = PillShape
                                ) {
                                    Text(
                                        text = tag,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (index % 2 == 0) TealTagText else SandTagText,
                                        modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Your emotions are allowed to move. Noticing them is already growth.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }
    }
}
