package com.example.dailyreminder.ui.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ReminderDto
import com.example.dailyreminder.data.repository.ReminderRepository
import kotlinx.coroutines.launch

class AddEditViewModel(private val repository: ReminderRepository) : ViewModel() {

    // LiveData untuk state input dan status
    private val _reminderTitle = MutableLiveData<String>()
    val reminderTitle: LiveData<String> get() = _reminderTitle

    private val _reminderDescription = MutableLiveData<String>()
    val reminderDescription: LiveData<String> get() = _reminderDescription

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String> get() = _selectedTime

    private val _isCompleted = MutableLiveData<Boolean>(false)
    val isCompleted: LiveData<Boolean> get() = _isCompleted

    private val _saveStatus = MutableLiveData<Boolean?>()
    val saveStatus: LiveData<Boolean?> get() = _saveStatus

    private var reminderId: Int? = null
    private var isEditMode = false

    fun initData(reminder: ReminderDto?) {
        if (reminder != null) {
            isEditMode = true
            reminderId = reminder.id
            _reminderTitle.value = reminder.title
            _reminderDescription.value = reminder.description
            _selectedDate.value = reminder.date
            _selectedTime.value = reminder.time
            _isCompleted.value = reminder.completed
        }
    }

    fun setTitle(title: String) {
        _reminderTitle.value = title
    }

    fun setDescription(description: String) {
        _reminderDescription.value = description
    }

    fun setDate(date: String) {
        _selectedDate.value = date
    }

    fun setTime(time: String) {
        _selectedTime.value = time
    }

    fun setCompleted(completed: Boolean) {
        _isCompleted.value = completed
    }

    fun saveReminder() {
        val title = _reminderTitle.value?.trim() ?: ""
        val description = _reminderDescription.value?.trim() ?: ""
        val date = _selectedDate.value ?: ""
        val time = _selectedTime.value ?: ""
        val completed = _isCompleted.value ?: false

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
            _saveStatus.value = false
            return
        }

        val reminder = ReminderDto(
            id = reminderId,
            title = title,
            description = description,
            date = date,
            time = time,
            completed = completed
        )

        viewModelScope.launch {
            try {
                val response = if (isEditMode) {
                    repository.updateReminder(reminderId!!, reminder)
                } else {
                    repository.createReminder(reminder)
                }
                _saveStatus.postValue(response.isSuccessful)
            } catch (e: Exception) {
                _saveStatus.postValue(false)
            }
        }
    }
}
