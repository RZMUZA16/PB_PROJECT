package com.example.dailyreminder.ui.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyreminder.R
import com.example.dailyreminder.data.model.ReminderDto
import com.example.dailyreminder.data.repository.ReminderRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddEditActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    private lateinit var switchCompleted: Switch
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var isEditMode = false
    private var reminderId: Int? = null

    private val repository = ReminderRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit)

        // Inisialisasi View
        etTitle = findViewById(R.id.etReminderTitle)
        etDescription = findViewById(R.id.etReminderDescription)
        btnDate = findViewById(R.id.btnSelectDate)
        btnTime = findViewById(R.id.btnSelectTime)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedTime = findViewById(R.id.tvSelectedTime)
        switchCompleted = findViewById(R.id.switchCompleted)
        btnSave = findViewById(R.id.btnSaveReminder)
        btnCancel = findViewById(R.id.btnCancel)

        // Cek jika mode edit
        val reminder = intent.getSerializableExtra("reminder") as? ReminderDto
        if (reminder != null) {
            isEditMode = true
            reminderId = reminder.id
            etTitle.setText(reminder.title)
            etDescription.setText(reminder.description)
            selectedDate = reminder.date
            selectedTime = reminder.time
            tvSelectedDate.text = selectedDate
            tvSelectedTime.text = selectedTime
            switchCompleted.isChecked = reminder.completed
        }

        btnDate.setOnClickListener { showDatePicker() }
        btnTime.setOnClickListener { showTimePicker() }
        btnSave.setOnClickListener { saveReminder() }
        btnCancel.setOnClickListener { finish() }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, y, m, d ->
            selectedDate = String.format("%04d-%02d-%02d", y, m + 1, d)
            tvSelectedDate.text = selectedDate
        }, year, month, day).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, h, m ->
            selectedTime = String.format("%02d:%02d", h, m)
            tvSelectedTime.text = selectedTime
        }, hour, minute, true).show()
    }

    private fun saveReminder() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val isCompleted = switchCompleted.isChecked

        if (title.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        val reminder = ReminderDto(
            id = reminderId,
            title = title,
            description = description,
            date = selectedDate,
            time = selectedTime,
            completed = isCompleted
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = if (isEditMode) {
                    repository.updateReminder(reminderId!!, reminder)
                } else {
                    repository.createReminder(reminder)
                }

                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddEditActivity, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddEditActivity, "Gagal menyimpan", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@AddEditActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
