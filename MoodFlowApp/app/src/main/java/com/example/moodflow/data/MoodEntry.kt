package com.example.moodflow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val moodIndex: Int,
    val energyLevel: Int,
    val contexts: String,
    val reflection: String
)
