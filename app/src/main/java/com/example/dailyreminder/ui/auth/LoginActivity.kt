package com.example.dailyreminder.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dailyreminder.R
import com.example.dailyreminder.data.repository.AuthRepository
import com.example.dailyreminder.ui.main.MainActivity
import com.example.dailyreminder.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvCreateAccount: TextView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvCreateAccount = findViewById(R.id.tvCreateAccount)
        sessionManager = SessionManager(this)

        sessionManager.getAuthToken()?.let { token ->
            if (token.isNotBlank()) {
                startMainAndFinish()
                return  // Skip the rest of onCreate
            }
        }

        btnLogin.setOnClickListener {
            performLogin()
        }

        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun startMainAndFinish() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
        )
        finish()
    }

    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val repository = AuthRepository()
                val response = repository.login(email, password)

                if (response.isSuccessful && response.body()?.accessToken != null) {
                    val token = response.body()?.accessToken!!
                    sessionManager.saveAuthToken(token)
                    sessionManager.saveUserId(id = response.body()?.user?.id ?: 0)
                    sessionManager.saveUserInfo(name = response.body()?.user?.name ?: "GUEST", email = response.body()?.user?.email
                        ?: "GUEST@EXAMPLE.COM")
                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()

                    // Navigasi ke MainActivity, dan clear history agar tidak bisa kembali ke Login
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Login gagal"
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Login error", e)
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
