package com.example.moodflow.ui.screens.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ColorLens
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.Teal
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.BottomNavBar
import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodOrb
import com.example.moodflow.ui.components.NavItem

@Composable
fun ProfileScreen(
    onNavigateTo: (NavItem) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp) // padding for floating bottom nav
        ) {
            ProfileHeader()
            
            ProfileIdentity()
            
            SettingsSection()
        }

        BottomNavBar(
            currentRoute = NavItem.YOU,
            onNavigate = onNavigateTo,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 28.dp)
    ) {
        Text(
            text = "Your Flow",
            style = MaterialTheme.typography.headlineLarge,
            color = Ink
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Manage your preferences and data.",
            style = MaterialTheme.typography.bodyMedium,
            color = Muted
        )
    }
}

@Composable
fun ProfileIdentity() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            MoodOrb(moodColors = MoodPalette[0], modifier = Modifier.size(100.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Finding balance",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Ink
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Flowing since May 2026",
            style = MaterialTheme.typography.labelMedium,
            color = Muted
        )
    }
}

@Composable
fun SettingsSection() {
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Preferences",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = Muted,
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )
        
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                SettingsRowToggle(
                    icon = Icons.Rounded.Notifications,
                    title = "Daily Reminders",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
                SettingsRowAction(
                    icon = Icons.Rounded.ColorLens,
                    title = "App Theme",
                    subtitle = "System Default",
                    onClick = {}
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Data & Privacy",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = Muted,
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )
        
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                SettingsRowAction(
                    icon = Icons.Rounded.Download,
                    title = "Export Data",
                    subtitle = "Save as CSV",
                    onClick = {}
                )
                SettingsRowAction(
                    icon = Icons.Rounded.Lock,
                    title = "Privacy & Security",
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun SettingsRowToggle(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .semantics { contentDescription = "Toggle $title" },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Muted, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Teal
            )
        )
    }
}

@Composable
fun SettingsRowAction(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Muted, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = Ink
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelMedium,
                        color = Muted
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = Color(0xFFA0A6AD),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
