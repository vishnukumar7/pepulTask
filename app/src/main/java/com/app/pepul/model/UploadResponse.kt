package com.app.pepul.model

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("result")
	val result: String,

	@field:SerializedName("file_type")
	val fileType: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
