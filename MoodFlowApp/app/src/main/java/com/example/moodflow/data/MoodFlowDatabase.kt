package com.example.moodflow.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoodEntry::class], version = 1, exportSchema = false)
abstract class MoodFlowDatabase : RoomDatabase() {
    abstract fun moodEntryDao(): MoodEntryDao
}
