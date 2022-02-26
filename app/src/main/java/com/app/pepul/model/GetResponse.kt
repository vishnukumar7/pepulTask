package com.app.pepul.model

import com.google.gson.annotations.SerializedName

data class GetResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class DataItem(

	@field:SerializedName("file")
	val file: String,

	@field:SerializedName("file_type")
	val fileType: String,

	@field:SerializedName("id")
	val id: String
)
