package com.example.moodflow.ui.screens.onboarding

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moodflow.R
import com.example.moodflow.theme.AppIconRadius
import com.example.moodflow.theme.AppIconSize
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.PagerDotActiveWidth
import com.example.moodflow.theme.PagerDotSize
import com.example.moodflow.theme.ScreenPaddingHorizontal
import com.example.moodflow.theme.TealDeep
import com.example.moodflow.theme.Line
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.MoodOrb
import com.example.moodflow.ui.components.PrimaryButton

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = ScreenPaddingHorizontal)
                .padding(top = 28.dp, bottom = 34.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            MoodOrb(
                moodColors = MoodPalette[3],
                modifier = Modifier.size(286.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.moodflow_logo),
                    contentDescription = "MoodFlow logo",
                    modifier = Modifier
                        .size(AppIconSize)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(AppIconRadius))
                )
                Text(
                    text = "MoodFlow",
                    style = MaterialTheme.typography.titleSmall,
                    color = Ink
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Understand the flow of your emotions",
                style = MaterialTheme.typography.displayLarge,
                color = Ink,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "A gentle space to reflect, understand and grow.",
                style = MaterialTheme.typography.bodyLarge,
                color = Muted,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Begin gently",
                onClick = onGetStarted,
                trailingContent = {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        tint = androidx.compose.ui.graphics.Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = PagerDotActiveWidth, height = PagerDotSize)
                        .clip(CircleShape)
                        .background(TealDeep)
                )
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(PagerDotSize)
                            .clip(CircleShape)
                            .background(Line.copy(alpha = 0.9f))
                    )
                }
            }
        }
    }
}
