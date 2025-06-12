package com.example.dailyreminder.data.api

import com.example.dailyreminder.data.model.ReminderDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReminderApiService {

    @GET("api/reminders")
    suspend fun getReminders(
        @Header("Authorization") token: String
    ): Response<List<ReminderDto>>

    @GET("api/reminders/{id}")
    suspend fun getReminderById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ReminderDto>

    @POST("api/reminders")
    suspend fun createReminder(
        @Header("Authorization") token: String,
        @Body reminder: ReminderDto
    ): Response<ReminderDto>

    @PUT("api/reminders/{id}")
    suspend fun updateReminder(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body reminder: ReminderDto
    ): Response<ReminderDto>

    @DELETE("api/reminders/{id}")
    suspend fun deleteReminder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void>
}
