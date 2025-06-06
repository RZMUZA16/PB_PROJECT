package com.example.dailyreminder.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("daily_reminder_prefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
    }

    // Simpan token
    fun saveAuthToken(token: String) {
        prefs.edit().putString(USER_TOKEN, token).apply()
    }

    // Ambil token (gunakan nama getAuthToken agar konsisten dengan penggunaan di kode lain)
    fun getAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    // Hapus semua session
    fun clearSession() {
        prefs.edit().clear().apply()
    }

    // Simpan info user (nama dan email)
    fun saveUserInfo(name: String, email: String) {
        prefs.edit()
            .putString(USER_NAME, name)
            .putString(USER_EMAIL, email)
            .apply()
    }

    // Ambil nama user, default "Guest"
    fun getUserName(): String? {
        return prefs.getString(USER_NAME, "Guest")
    }

    // Ambil email user, default "guest@example.com"
    fun getUserEmail(): String? {
        return prefs.getString(USER_EMAIL, "guest@example.com")
    }
}
