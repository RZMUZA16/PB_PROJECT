package com.example.dailyreminder.ui.main

import androidx.lifecycle.*
import com.example.dailyreminder.data.repository.ReminderRepository
import com.example.dailyreminder.model.Reminder
import com.example.dailyreminder.data.model.toDomain  // Pastikan ini di-import
import kotlinx.coroutines.launch

class MainViewModel(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _reminders = MutableLiveData<List<Reminder>>()
    val reminders: LiveData<List<Reminder>> = _reminders

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchReminders(token: String) {
        viewModelScope.launch {
            try {
                val response = reminderRepository.getReminders(token)
                if (response.isSuccessful) {
                    val dtoList = response.body() ?: emptyList()
                    val reminderList = dtoList.map { it.toDomain() }
                    _reminders.value = reminderList
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun refreshReminders(token: String) {
        fetchReminders(token)
    }
}
