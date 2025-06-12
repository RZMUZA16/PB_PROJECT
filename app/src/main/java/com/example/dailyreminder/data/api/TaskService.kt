package com.example.dailyreminder.com.example.dailyreminder.data.api

import com.example.dailyreminder.data.model.AddTask
import com.example.dailyreminder.data.model.TasksItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskService {

    @GET("api/tasks")
    suspend fun getTasks(): Response<List<TasksItem>>

    @POST("api/tasks")
    suspend fun addTasks(
        @Body addTask: AddTask
    ): Response<TasksItem>

}