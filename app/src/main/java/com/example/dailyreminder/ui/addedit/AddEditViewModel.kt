package com.example.dailyreminder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyreminder.data.model.toDto
import com.example.dailyreminder.data.repository.ReminderRepository
import com.example.dailyreminder.model.Reminder
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val repository: ReminderRepository
) : ViewModel() {

    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> get() = _saveResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun saveReminder(
        token: String,
        title: String,
        description: String,
        date: String,
        time: String,
        isCompleted: Boolean
    ) {
        if (title.isBlank()) {
            _errorMessage.value = "Title is required"
            return
        }

        val reminder = Reminder(
            title = title,
            description = description,
            date = date,
            time = time,
            isCompleted = isCompleted
        )

        viewModelScope.launch {
            try {
                repository.createReminder(token, reminder.toDto())
                _saveResult.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
                _saveResult.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
