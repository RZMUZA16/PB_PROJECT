package com.example.dailyreminder.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dailyreminder.data.model.UserDto
import com.example.dailyreminder.data.repository.AuthRepository
import com.example.dailyreminder.databinding.ActivityRegisterBinding
import com.example.dailyreminder.ui.main.MainActivity
import com.example.dailyreminder.utils.SessionManager
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        binding.btnRegister.setOnClickListener { performRegister() }
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun performRegister() {
        val name             = binding.etName.text.toString().trim()
        val email            = binding.etEmail.text.toString().trim()
        val password         = binding.etPassword.text.toString()
        val passwordConfirm  = binding.etPasswordConfirm.text.toString()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val userDto = UserDto(
            name             = name,
            email            = email,
            password         = password,
            passwordConfirm  = passwordConfirm
        )

        lifecycleScope.launch {
            try {
                val response = AuthRepository().register(userDto)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!

                    sessionManager.saveAuthToken(body.accessToken)
                    sessionManager.saveUserInfo(body.user.name, body.user.email)
                    sessionManager.saveUserId(body.user.id)

                    Toast.makeText(
                        this@RegisterActivity,
                        body.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(this@RegisterActivity, MainActivity::class.java)
                            .apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                    )
                    finish()

                } else {
                    val errorBody = response.errorBody()?.string().orEmpty()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Register gagal: $errorBody",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Error: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
