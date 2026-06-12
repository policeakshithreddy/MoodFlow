package com.example.moodflow.ui.screens.journey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.data.MoodEntry
import com.example.moodflow.data.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

enum class JourneyFilter(val label: String, val isVisible: Boolean = true) {
    ALL("All"),
    MOMENTS("Moments"),
    REFLECTIONS("Reflections"),
    TAGS("Tags"),
    
    // Future filters (invisible for now but supported in architecture)
    ENERGY("Energy", isVisible = false),
    CREATIVITY("Creativity", isVisible = false),
    WORK("Work", isVisible = false),
    RELATIONSHIPS("Relationships", isVisible = false),
    SLEEP("Sleep", isVisible = false)
}

data class JourneyState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val selectedFilter: JourneyFilter = JourneyFilter.ALL,
    val groupedEntries: Map<String, List<MoodEntry>> = emptyMap(),
    val availableFilters: List<JourneyFilter> = JourneyFilter.entries.filter { it.isVisible }
)

@HiltViewModel
class JourneyViewModel @Inject constructor(
    repository: MoodRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _isSearchActive = MutableStateFlow(false)
    private val _selectedFilter = MutableStateFlow(JourneyFilter.ALL)

    val state: StateFlow<JourneyState> = combine(
        repository.getAllEntries(),
        _searchQuery,
        _isSearchActive,
        _selectedFilter
    ) { entries, query, searchActive, filter ->
        val filtered = entries.filter { entry ->
            val matchesSearch = if (query.isBlank()) true else {
                entry.reflection?.contains(query, ignoreCase = true) == true ||
                entry.contexts.contains(query, ignoreCase = true)
            }
            
            val matchesFilter = when (filter) {
                JourneyFilter.ALL -> true
                JourneyFilter.MOMENTS -> true // Simplified for prototype
                JourneyFilter.REFLECTIONS -> !entry.reflection.isNullOrBlank()
                JourneyFilter.TAGS -> entry.contexts.isNotBlank()
                else -> true
            }
            
            matchesSearch && matchesFilter
        }

        JourneyState(
            searchQuery = query,
            isSearchActive = searchActive,
            selectedFilter = filter,
            groupedEntries = groupEntriesByDate(filtered)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = JourneyState(isLoading = true)
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleSearch() {
        _isSearchActive.update { !it }
        if (!_isSearchActive.value) {
            _searchQuery.value = ""
        }
    }

    fun onFilterSelected(filter: JourneyFilter) {
        _selectedFilter.value = filter
    }

    private fun groupEntriesByDate(entries: List<MoodEntry>): Map<String, List<MoodEntry>> {
        val today = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate()
        val yesterday = today.minusDays(1)
        val formatter = DateTimeFormatter.ofPattern("MMM d")

        return entries.groupBy { entry ->
            val entryDate = Instant.ofEpochMilli(entry.timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            
            when {
                entryDate == today -> "Today"
                entryDate == yesterday -> "Yesterday"
                else -> entryDate.format(formatter)
            }
        }.toSortedMap { a, b ->
            // Simple sorting to keep Today > Yesterday > Older dates.
            when {
                a == "Today" -> -1
                b == "Today" -> 1
                a == "Yesterday" -> -1
                b == "Yesterday" -> 1
                else -> b.compareTo(a)
            }
        }
    }
}
