
package com.example.dailyreminder.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatReminderDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault()))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatReminderTime(time: LocalTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun parseApiDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun parseApiTime(timeString: String): LocalTime {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun toApiDateString(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // Format untuk API
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun toApiTimeString(time: LocalTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH:mm")) // Format untuk API
    }
}