package com.example.moodflow.ui.screens.journey

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.Muted
import com.example.moodflow.ui.components.AmbientBackground
import com.example.moodflow.ui.components.NavItem

@Composable
fun JourneyScreen(
    onNavigate: (NavItem) -> Unit,
    onMoodClick: () -> Unit, // Navigate to details
    viewModel: JourneyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground()

        Column(modifier = Modifier.fillMaxSize()) {
            JourneyHeader(
                onSearchClick = { viewModel.toggleSearch() },
                onFilterClick = { /* Optional bottom sheet for future */ }
            )

            AnimatedVisibility(
                visible = state.isSearchActive,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp, vertical = 8.dp)
                        .background(Color(0x33FFFFFF), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    if (state.searchQuery.isEmpty()) {
                        Text("Search memories...", color = Muted)
                    }
                    BasicTextField(
                        value = state.searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = Ink),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            FilterTabs(
                filters = state.availableFilters,
                selectedFilter = state.selectedFilter,
                onFilterSelected = { viewModel.onFilterSelected(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp) // Leave space for BottomNavBar
            ) {
                if (state.groupedEntries.isEmpty() && !state.isLoading) {
                    item {
                        Text(
                            text = "No memories found.",
                            color = Muted,
                            modifier = Modifier.padding(28.dp)
                        )
                    }
                }

                state.groupedEntries.forEach { (dateHeader, entries) ->
                    item {
                        JourneySectionHeader(text = dateHeader)
                    }
                    
                    items(entries, key = { it.id }) { entry ->
                        MemoryCard(
                            entry = entry,
                            onCardClick = onMoodClick, // Passing click for navigation
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
