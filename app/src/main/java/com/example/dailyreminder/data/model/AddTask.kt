package com.example.dailyreminder.data.model

import com.google.gson.annotations.SerializedName

data class AddTask(

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("tanggal_tugas")
	val tanggalTugas: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("judul")
	val judul: String
)
