package com.example.dailyreminder.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyreminder.R
import com.example.dailyreminder.ui.auth.LoginActivity
import com.example.dailyreminder.utils.SessionManager

class AccountActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnBack: ImageButton
    private lateinit var btnEditProfile: Button
    private lateinit var btnChangePassword: Button

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        sessionManager = SessionManager(this)

        // View binding
        tvName = findViewById(R.id.NameAccount)
        tvEmail = findViewById(R.id.emailAccount)
        btnLogout = findViewById(R.id.btnLogout)
        btnBack = findViewById(R.id.btnBack)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnChangePassword = findViewById(R.id.btnChangePassword)

        // Ambil data dari session
        tvName.text = sessionManager.getUserName()
        tvEmail.text = sessionManager.getUserEmail()

        // Aksi tombol back
        btnBack.setOnClickListener {
            finish()
        }

        // Aksi edit profile
        btnEditProfile.setOnClickListener {
            Toast.makeText(this, "Edit Profile belum diimplementasi", Toast.LENGTH_SHORT).show()
        }

        // Aksi ganti password
        btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Change Password belum diimplementasi", Toast.LENGTH_SHORT).show()
        }

        // Aksi logout
        btnLogout.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
