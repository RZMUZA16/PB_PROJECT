// com/example/dailyreminder/DailyReminderApp.kt
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

class DailyReminder: Application() {


    val BASE_URL = "http://localhost:8000/api/"


    val sessionManager: SessionManager by lazy {
        SessionManager(this)
    }


    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = sessionManager.getAuthToken()
        val newRequest = if (token != null) {

            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        chain.proceed(newRequest)
    }


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API Service instances
    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val reminderApiService: ReminderApiService by lazy {
        retrofit.create(ReminderApiService::class.java)
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

    }
}