package com.app.pepul.model

import com.google.gson.annotations.SerializedName

data class GetRequest(

	@field:SerializedName("lastFetchId")
	val lastFetchId: String
)
