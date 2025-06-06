package com.example.dailyreminder.ui.main

import androidx.lifecycle.*
import com.example.dailyreminder.data.repository.ReminderRepository
import com.example.dailyreminder.model.Reminder
import kotlinx.coroutines.launch

class MainViewModel(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _reminders = MutableLiveData<List<Reminder>>()
    val reminders: LiveData<List<Reminder>> = _reminders

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchReminders()
    }

    private fun fetchReminders() {
        viewModelScope.launch {
            try {
                val list = reminderRepository.getReminders()
                _reminders.value = list
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun refreshReminders() {
        fetchReminders()
    }
}
