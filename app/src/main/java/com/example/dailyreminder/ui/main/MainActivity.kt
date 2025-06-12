package com.example.dailyreminder.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dailyreminder.com.example.dailyreminder.adapter.TasksAdapter
import com.example.dailyreminder.data.api.RetrofitInstance
import com.example.dailyreminder.databinding.ActivityMainBinding
import com.example.dailyreminder.ui.account.AccountActivity
import com.example.dailyreminder.ui.addTask.AddTasksActivity
import com.example.dailyreminder.utils.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: TasksAdapter
    private val addTaskLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // User just added a task → reload the list
            loadTasks()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // Setup RecyclerView
        adapter = TasksAdapter()
        binding.rvTasks.adapter = adapter

        // Load tasks
        loadTasks()

        // Tambah Note (nanti bisa diarahkan ke AddNoteActivity)
        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this, AddTasksActivity::class.java)
            addTaskLauncher.launch(intent)
        }

        binding.imgAccount.setOnClickListener {
            startActivity(Intent(this@MainActivity, AccountActivity::class.java))
        }

    }

    private fun loadTasks() {
        val api = RetrofitInstance.provideTaskService(sessionManager)
        Log.d(TAG, "Starting loadTasks()")  // entry point

        lifecycleScope.launch {
            try {
                Log.d(TAG, "Calling GET /api/tasks")
                val resp = api.getTasks()

                if (resp.isSuccessful) {
                    val tasksList = resp.body() ?: emptyList()
                    Log.i(TAG, "Loaded ${tasksList.size} tasks successfully")
                    adapter.submitList(tasksList)
                } else {
                    Log.w(TAG, "GET /api/tasks failed with HTTP ${resp.code()} – ${resp.errorBody()?.string()}")
                    Toast.makeText(
                        this@MainActivity,
                        "Gagal memuat tugas: ${resp.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network or parsing error in loadTasks()", e)
                Toast.makeText(
                    this@MainActivity,
                    "Kesalahan jaringan: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
