package com.example.dailyreminder.data.api

import com.example.dailyreminder.com.example.dailyreminder.data.api.TaskService
import com.example.dailyreminder.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8000/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofitAuth by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** Use this for your login & register calls */
    val authApiService: AuthApiService by lazy {
        retrofitAuth.create(AuthApiService::class.java)
    }

    // 2. Auth interceptor to append the Bearer token automatically
    private fun provideAuthInterceptor(sessionManager: SessionManager) = Interceptor { chain ->
        val original = chain.request()
        val token = sessionManager.getAuthToken()
        val request = if (token != null) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else original
        chain.proceed(request)
    }

    // 3. Build OkHttpClient
    private fun provideOkHttpClient(sessionManager: SessionManager) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(provideAuthInterceptor(sessionManager))
        .build()

    // 4. Build Retrofit
    fun provideRetrofit(sessionManager: SessionManager): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(sessionManager))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 5. Expose APIs
    fun provideTaskService(sessionManager: SessionManager): TaskService =
        provideRetrofit(sessionManager).create(TaskService::class.java)
}
