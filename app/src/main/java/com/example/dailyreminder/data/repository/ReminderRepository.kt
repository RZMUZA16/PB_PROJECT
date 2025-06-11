package com.example.dailyreminder.data.repository

import com.example.dailyreminder.data.model.ReminderDto
import com.example.dailyreminder.data.api.ReminderApiService
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReminderRepository(private val api: ReminderApiService) {

    suspend fun getReminders(token: String): Response<List<ReminderDto>> {
        return api.getReminders("Bearer $token")
    }

    suspend fun getReminderById(token: String, id: Int): Response<ReminderDto> {
        return api.getReminderById("Bearer $token", id)
    }

    suspend fun createReminder(token: String, reminder: ReminderDto): Response<ReminderDto> {
        return api.createReminder("Bearer $token", reminder)
    }

    suspend fun updateReminder(token: String, id: Int, reminder: ReminderDto): Response<ReminderDto> {
        return api.updateReminder("Bearer $token", id, reminder)
    }

    suspend fun deleteReminder(token: String, id: Int): Response<Void> {
        return api.deleteReminder("Bearer $token", id)
    }

    suspend fun addReminder(token: String, title: String, description: String): Response<ReminderDto> {
        val currentDate = LocalDate.now()
        val currentTime = LocalTime.now()
        val date = currentDate.toString() // format: YYYY-MM-DD
        val time = currentTime.format(DateTimeFormatter.ofPattern("HH:mm")) // format: HH:mm

        val reminder = ReminderDto(
            title = title,
            description = description,
            date = date,
            time = time,
            isCompleted = false
        )
        return api.createReminder("Bearer $token", reminder)
    }
}
