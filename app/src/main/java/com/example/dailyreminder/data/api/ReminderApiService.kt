package com.example.dailyreminder.data.api

import ReminderDto
import retrofit2.Response
import retrofit2.http.*

interface ReminderApiService {

    @GET("reminders")
    suspend fun getReminders(
        @Header("Authorization") token: String
    ): Response<List<ReminderDto>>

    @GET("reminders/{id}")
    suspend fun getReminderById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<ReminderDto>

    @POST("reminders")
    suspend fun createReminder(
        @Header("Authorization") token: String,
        @Body reminder: ReminderDto
    ): Response<ReminderDto>

    @PUT("reminders/{id}")
    suspend fun updateReminder(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body reminder: ReminderDto
    ): Response<ReminderDto>

    @DELETE("reminders/{id}")
    suspend fun deleteReminder(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Void>
}
