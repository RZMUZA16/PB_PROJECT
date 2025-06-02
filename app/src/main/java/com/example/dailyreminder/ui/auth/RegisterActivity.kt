package com.example.dailyreminder.ui.auth

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dailyreminder.R
import com.example.dailyreminder.data.repository.AuthRepository
import com.example.dailyreminder.data.model.UserDto
import kotlinx.coroutines.launch
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etNumber: EditText
    private lateinit var btnDatePicker: Button
    private lateinit var btnRegister: Button
    private lateinit var tvCreateAccount: TextView

    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi view
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etNumber = findViewById(R.id.etNumber)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnRegister = findViewById(R.id.btnRegister)
        tvCreateAccount = findViewById(R.id.tvCreateAccount)

        btnDatePicker.setOnClickListener { showDatePicker() }
        btnRegister.setOnClickListener { performRegister() }
        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, y, m, d ->
                selectedDate = "$y-${m + 1}-$d"
                btnDatePicker.text = selectedDate
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun performRegister() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val phone = etNumber.text.toString().trim()
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || selectedDate.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val userDto = UserDto(
            name = name,
            email = email,
            password = password,
            phone = phone,
            gender = gender,
            birthDate = selectedDate
        )

        lifecycleScope.launch {
            try {
                val repository = AuthRepository() // Pastikan ini sudah diatur dependency-nya
                val response = repository.register(userDto)
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Register berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Register gagal: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
