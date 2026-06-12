package com.example.moodflow.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {
    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

    fun formatJourneyTime(timestamp: Long): String {
        val isToday = android.text.format.DateUtils.isToday(timestamp)
        val dateString = if (isToday) "Today" else dateFormat.format(Date(timestamp))
        val timeString = timeFormat.format(Date(timestamp))
        return "$dateString · $timeString"
    }
}
