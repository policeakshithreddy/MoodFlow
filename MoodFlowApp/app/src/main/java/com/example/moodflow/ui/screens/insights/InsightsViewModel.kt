package com.example.moodflow.ui.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.data.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class InsightPeriod {
    WEEKLY,
    MONTHLY,
    ALL_TIME
}

enum class InsightCategory {
    OVERALL,
    ENERGY,
    RELATIONSHIPS,
    CREATIVITY,
    SLEEP
}

data class InsightObservation(
    val id: String,
    val text: String,
    val moodIndex: Int // Links to MoodPalette for dot color (0=Reflective, 1=Calm, 2=Focused, 3=Joyful)
)

data class InsightState(
    val isLoading: Boolean = true,
    val isEmpty: Boolean = false,
    val activePeriod: InsightPeriod = InsightPeriod.WEEKLY,
    val activeCategory: InsightCategory = InsightCategory.OVERALL,
    val heroInsight: String = "",
    val observations: List<InsightObservation> = emptyList(),
    // Chart flow data mapping timestamps to values (0f to 1f) representing emotional levels
    val flowData: List<Pair<Long, Float>> = emptyList()
)

@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {

    private val _state = MutableStateFlow(InsightState())
    val state: StateFlow<InsightState> = _state.asStateFlow()

    init {
        loadInsights()
    }

    private fun loadInsights() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            repository.getAllEntries().collect { entries ->
                if (entries.isEmpty()) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isEmpty = true
                    )
                } else {
                    // Populate with actual or dummy data for now
                    val now = System.currentTimeMillis()
                    val oneDay = 86_400_000L
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isEmpty = false,
                        heroInsight = "You felt calmer this week.",
                        observations = listOf(
                            InsightObservation("1", "Creative moments appeared mostly after work.", 2), // Focused/Sand
                            InsightObservation("2", "High energy days led to more reflections.", 0), // Reflective/Teal
                            InsightObservation("3", "Balance improved compared to last month.", 1) // Calm/TealSoft
                        ),
                        flowData = listOf(
                            Pair(now - oneDay * 6, 0.4f),
                            Pair(now - oneDay * 5, 0.6f),
                            Pair(now - oneDay * 4, 0.7f),
                            Pair(now - oneDay * 3, 0.3f),
                            Pair(now - oneDay * 2, 0.5f),
                            Pair(now - oneDay * 1, 0.8f),
                            Pair(now, 0.6f)
                        )
                    )
                }
            }
        }
    }

    fun setPeriod(period: InsightPeriod) {
        _state.value = _state.value.copy(activePeriod = period)
        loadInsights() // Reload based on new period
    }

    fun setCategory(category: InsightCategory) {
        _state.value = _state.value.copy(activeCategory = category)
        loadInsights() // Reload based on new category
    }
}
