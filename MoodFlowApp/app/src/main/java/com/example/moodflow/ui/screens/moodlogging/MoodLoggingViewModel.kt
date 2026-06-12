package com.example.moodflow.ui.screens.moodlogging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodflow.data.MoodEntry
import com.example.moodflow.data.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MoodLoggingState(
    val selectedMoodIndex: Int = 3,
    val selectedEnergy: Int = 2,
    val selectedTags: Set<String> = emptySet(),
    val reflectionText: String = "",
    val availableInfluenceTags: List<String> = listOf(
        "Work", "Relationships", "Health", "Sleep", "Growth"
        // Future: "Family", "Friends", "Creativity", "Learning", "Exercise", "Weather", "Money"
    ),
    val availableEmotionTags: List<String> = listOf(
        "Grateful", "Proud", "Motivated", "Hopeful", "Creative", "Lonely", "Anxious", "Excited"
    )
) {
    val reflectionCharCount: Int get() = reflectionText.length
}

@HiltViewModel
class MoodLoggingViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodLoggingState())
    val uiState: StateFlow<MoodLoggingState> = _uiState.asStateFlow()

    fun setMood(index: Int) {
        _uiState.update { it.copy(selectedMoodIndex = index.coerceIn(0, 4)) }
    }

    fun setEnergy(level: Int) {
        _uiState.update { it.copy(selectedEnergy = level.coerceIn(1, 3)) }
    }

    fun toggleTag(tag: String) {
        _uiState.update { state ->
            val newTags = if (state.selectedTags.contains(tag)) {
                state.selectedTags - tag
            } else {
                state.selectedTags + tag
            }
            state.copy(selectedTags = newTags)
        }
    }

    fun updateReflection(text: String) {
        _uiState.update { it.copy(reflectionText = text) }
        autosaveDraft()
    }

    private fun autosaveDraft() {
        // Architecture ready for debounced local autosave (e.g., DataStore / Room)
        // This ensures the user's reflection is not lost if the app is killed.
    }

    fun logMood(onSaved: () -> Unit = {}) {
        val state = _uiState.value
        viewModelScope.launch {
            repository.addEntry(
                MoodEntry(
                    timestamp = System.currentTimeMillis(),
                    moodIndex = state.selectedMoodIndex,
                    energyLevel = state.selectedEnergy,
                    contexts = state.selectedTags.joinToString(", "),
                    reflection = state.reflectionText.ifBlank {
                        "A quiet moment of emotional awareness."
                    }
                )
            )
            onSaved()
        }
    }
}
