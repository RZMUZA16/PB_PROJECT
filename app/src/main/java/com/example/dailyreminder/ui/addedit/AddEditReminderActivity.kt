package com.example.dailyreminder.ui.addedit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyreminder.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddEditReminderActivity : AppCompatActivity() {

    private lateinit var etReminderTitle: EditText
    private lateinit var etReminderDescription: EditText
    private lateinit var btnSelectDate: Button
    private lateinit var btnSelectTime: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchCompleted: Switch
    private lateinit var btnCancel: Button
    private lateinit var btnSaveReminder: Button

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit)

        // Bind Views
        etReminderTitle = findViewById(R.id.etReminderTitle)
        etReminderDescription = findViewById(R.id.etReminderDescription)
        btnSelectDate = findViewById(R.id.btnSelectDate)
        btnSelectTime = findViewById(R.id.btnSelectTime)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedTime = findViewById(R.id.tvSelectedTime)
        switchCompleted = findViewById(R.id.switchCompleted)
        btnCancel = findViewById(R.id.btnCancel)
        btnSaveReminder = findViewById(R.id.btnSaveReminder)

        setupListeners()
    }

    private fun setupListeners() {
        btnSelectDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                calendar.set(Calendar.YEAR, y)
                calendar.set(Calendar.MONTH, m)
                calendar.set(Calendar.DAY_OF_MONTH, d)
                val dateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
                tvSelectedDate.text = dateFormat.format(calendar.time)
            }, year, month, day).show()
        }

        btnSelectTime.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, h, m ->
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, m)
                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                tvSelectedTime.text = timeFormat.format(calendar.time)
            }, hour, minute, false).show()
        }

        btnCancel.setOnClickListener {
            finish()
        }

        btnSaveReminder.setOnClickListener {
            val title = etReminderTitle.text.toString().trim()
            val description = etReminderDescription.text.toString().trim()
            val date = tvSelectedDate.text.toString()
            val time = tvSelectedTime.text.toString()
            val isCompleted = switchCompleted.isChecked

            if (title.isEmpty()) {
                etReminderTitle.error = "Title is required"
                return@setOnClickListener
            }

            // TODO: Integrasi dengan ViewModel atau API
            Toast.makeText(this, "Reminder saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
