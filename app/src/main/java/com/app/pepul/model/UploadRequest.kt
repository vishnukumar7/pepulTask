package com.app.pepul.model

import com.google.gson.annotations.SerializedName

data class UploadRequest(

	@field:SerializedName("fileToUpload")
	val fileToUpload: String
)
