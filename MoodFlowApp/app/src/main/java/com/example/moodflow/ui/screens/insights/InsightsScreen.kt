package com.example.moodflow.ui.screens.insights

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.BottomNavBar
import com.example.moodflow.ui.components.NavItem

@Composable
fun InsightsScreen(
    onNavigateTo: (NavItem) -> Unit,
    viewModel: InsightsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp) // padding for floating bottom nav
        ) {
            InsightsHeader()

            if (state.isEmpty) {
                EmptyInsightsState()
            } else {
                KeyDiscoveryCard(heroInsight = state.heroInsight)
                ObservationList(observations = state.observations)
                EmotionalFlowChart()
            }
        }

        BottomNavBar(
            currentRoute = NavItem.INSIGHTS,
            onNavigate = onNavigateTo,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
