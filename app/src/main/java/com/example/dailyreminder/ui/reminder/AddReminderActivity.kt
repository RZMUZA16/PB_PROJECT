package com.example.dailyreminder.com.example.dailyreminder.ui.reminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyreminder.data.model.ReminderDto
import com.example.dailyreminder.data.repository.ReminderRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AddReminderViewModel(private val repository: ReminderRepository) : ViewModel() {

    private val _addResult = MutableLiveData<Result<String>>()
    val addResult: LiveData<Result<String>> = _addResult

    fun addReminder(title: String, description: String, token: String) {
        viewModelScope.launch {
            try {
                val response: Response<ReminderDto> = repository.addReminder(token, title, description)
                if (response.isSuccessful) {
                    _addResult.postValue(Result.success("Reminder added successfully"))
                } else {
                    _addResult.postValue(Result.failure(Exception("Failed: ${response.message()}")))
                }
            } catch (e: Exception) {
                _addResult.postValue(Result.failure(e))
            }
        }
    }
}
