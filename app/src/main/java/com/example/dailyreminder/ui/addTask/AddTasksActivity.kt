package com.example.dailyreminder.ui.addTask

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.dailyreminder.R
import com.example.dailyreminder.data.api.RetrofitInstance
import com.example.dailyreminder.data.model.AddTask
import com.example.dailyreminder.databinding.ActivityAddTasksBinding
import com.example.dailyreminder.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTasksBinding
    private lateinit var sessionManager: SessionManager
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sessionManager = SessionManager(this)


        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Show date picker on click
        binding.btnSelectDate.setOnClickListener {
            showDatePicker()
        }

        // Submit new task
        binding.btnSubmit.setOnClickListener {
            submitTask()
        }

        // Optional: Cancel button simply finishes
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun submitTask() {
        val judul = binding.etJudul.text.toString().trim()
        val keterangan = binding.etKeterangan.text.toString().trim()
        val tanggal = binding.etTanggal.text.toString().trim()
        val storedUserId = sessionManager.getUserId()

        if (storedUserId == null || storedUserId < 0) {
            Toast.makeText(this, "User belum terautentikasi", Toast.LENGTH_SHORT).show()
            return
        }

        // Basic validation
        if (judul.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(this, "Judul dan tanggal harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        // Build request DTO
        val addTask = AddTask(
            userId      = storedUserId,
            judul       = judul,
            keterangan  = keterangan,
            tanggalTugas= tanggal
        )

        // Obtain API with auth interceptor
        val api = RetrofitInstance.provideTaskService(sessionManager)

        // Launch coroutine
        lifecycleScope.launch {
            try {
                val response = api.addTasks(addTask)
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@AddTasksActivity,
                        "Tugas berhasil ditambahkan",
                        Toast.LENGTH_LONG
                    ).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    val errorMsg = response.errorBody()?.string().orEmpty()
                    Toast.makeText(
                        this@AddTasksActivity,
                        "Gagal: $errorMsg",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@AddTasksActivity,
                    "Kesalahan jaringan: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun showDatePicker() {
        val now = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }.time.let {
                    binding.etTanggal.text = dateFormat.format(it)
                }
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}