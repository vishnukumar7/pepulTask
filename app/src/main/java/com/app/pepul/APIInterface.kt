package com.app.pepul

import com.app.pepul.model.*
import retrofit2.Response
import retrofit2.http.POST

interface APIInterface {

    @POST("uploader.php")
    suspend fun uploadImageVideo(uploadRequest: UploadRequest): Response<UploadResponse>

    @POST("delete.php")
    suspend fun deleteImageVideo(deleteRequest: DeleteRequest): Response<DeleteResponse>

    @POST("select.php")
    suspend fun getImageVideo(getRequest: GetRequest): Response<GetResponse>
}