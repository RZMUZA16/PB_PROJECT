package com.example.dailyreminder

import android.app.Application
import com.example.dailyreminder.data.api.AuthApiService
import com.example.dailyreminder.data.api.ReminderApiService
import com.example.dailyreminder.utils.SessionManager
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DailyReminderApp : Application() {

    companion object {
        lateinit var instance: DailyReminderApp
            private set

        lateinit var authApiService: AuthApiService
            private set

        lateinit var reminderApiService: ReminderApiService
            private set
    }

    private val BASE_URL = "http://10.0.2.2:8000/api/"

    val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = sessionManager.getAuthToken()
            val newRequest = if (!token.isNullOrEmpty()) {
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } else {
                originalRequest
            }
            chain.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApiService = retrofit.create(AuthApiService::class.java)
        reminderApiService = retrofit.create(ReminderApiService::class.java)
    }
}
