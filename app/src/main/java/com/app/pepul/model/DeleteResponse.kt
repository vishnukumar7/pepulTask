package com.app.pepul.model

import com.google.gson.annotations.SerializedName

data class DeleteResponse(

	@field:SerializedName("result")
	val result: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)
