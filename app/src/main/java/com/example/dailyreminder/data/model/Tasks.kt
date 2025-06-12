package com.example.dailyreminder.data.model

import com.google.gson.annotations.SerializedName

data class Tasks(

	@field:SerializedName("Tasks")
	val tasks: List<TasksItem>
)

data class TasksItem(

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("tanggal_tugas")
	val tanggalTugas: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("task_id")
	val taskId: Int,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("user")
	val user: User
)
