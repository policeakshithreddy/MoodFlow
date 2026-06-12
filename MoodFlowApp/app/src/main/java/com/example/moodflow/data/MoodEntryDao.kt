package com.example.moodflow.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodEntryDao {
    @Insert
    suspend fun insert(entry: MoodEntry)

    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getAllEntries(): Flow<List<MoodEntry>>
    
    @Query("SELECT * FROM mood_entries WHERE timestamp >= :since ORDER BY timestamp ASC")
    fun getEntriesSince(since: Long): Flow<List<MoodEntry>>
}
