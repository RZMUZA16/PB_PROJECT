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

    fun saveAuthToken(token: String) {
        prefs.edit().putString(USER_TOKEN, token).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun saveUserInfo(name: String, email: String) {
        prefs.edit()
            .putString(USER_NAME, name)
            .putString(USER_EMAIL, email)
            .apply()
    }

    fun getUserName(): String? {
        return prefs.getString(USER_NAME, "Guest")
    }

    fun getUserEmail(): String? {
        return prefs.getString(USER_EMAIL, "guest@example.com")
    }
}
