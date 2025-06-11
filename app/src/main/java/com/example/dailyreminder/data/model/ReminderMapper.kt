package com.example.dailyreminder.data.model

import com.example.dailyreminder.model.Reminder

// Konversi dari DTO ke Domain
fun ReminderDto.toDomain(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        isCompleted = isCompleted
    )
}

// Konversi dari Domain ke DTO
fun Reminder.toDto(): ReminderDto {
    return ReminderDto(
        id = id,
        title = title,
        description = description,
        date = date,
        time = time,
        isCompleted = isCompleted
    )
}
