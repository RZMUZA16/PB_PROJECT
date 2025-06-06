package com.example.dailyreminder.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyreminder.R
import com.example.dailyreminder.data.model.ReminderDto
import com.example.dailyreminder.data.repository.ReminderRepository
import com.example.dailyreminder.ui.account.AccountActivity
import com.example.dailyreminder.ui.reminder.AddReminderActivity
import com.example.dailyreminder.utils.SessionManager
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var tvKegiatan: TextView
    private lateinit var lvKegiatan: ListView
    private lateinit var btnAddNote: ImageButton
    private lateinit var imgAccount: ImageView

    private val reminderRepository = ReminderRepository()
    private lateinit var sessionManager: SessionManager

    private var selectedDate: String = getTodayDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        calendarView = findViewById(R.id.calendarView)
        tvKegiatan = findViewById(R.id.tvKegiatan)
        lvKegiatan = findViewById(R.id.lvKegiatan)
        btnAddNote = findViewById(R.id.btnAddNote)
        imgAccount = findViewById(R.id.imgAccount)

        // Tampilkan kegiatan hari ini
        loadReminders(selectedDate)

        // Ketika tanggal dipilih
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$year-${month + 1}-$dayOfMonth"
            loadReminders(selectedDate)
        }

        // Buka activity tambah reminder
        btnAddNote.setOnClickListener {
            val intent = Intent(this, AddReminderActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)
        }

        // Buka halaman akun
        imgAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }

    private fun loadReminders(date: String) {
        val token = sessionManager.fetchAuthToken()
        if (token == null) {
            Toast.makeText(this, "Silakan login dulu", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    reminderRepository.getReminders(token, date)
                }

                if (response.isSuccessful) {
                    val reminders = response.body() ?: emptyList()
                    val reminderTexts = reminders.map { it.title }
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, reminderTexts)
                    lvKegiatan.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Gagal ambil data: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy-M-d", Locale.getDefault())
        return sdf.format(Date())
    }
}
