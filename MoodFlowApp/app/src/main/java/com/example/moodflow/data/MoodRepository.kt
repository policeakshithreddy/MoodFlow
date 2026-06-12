package com.example.moodflow.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoodRepository @Inject constructor(
    private val dao: MoodEntryDao
) {
    fun getAllEntries(): Flow<List<MoodEntry>> = dao.getAllEntries()
    
    fun getEntriesSince(timestamp: Long): Flow<List<MoodEntry>> = dao.getEntriesSince(timestamp)
    
    suspend fun addEntry(entry: MoodEntry) {
        dao.insert(entry)
    }
}
