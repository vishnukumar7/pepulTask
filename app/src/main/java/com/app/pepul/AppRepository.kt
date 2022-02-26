package com.app.pepul

import com.app.pepul.model.DeleteRequest
import com.app.pepul.model.GetRequest
import com.app.pepul.model.UploadRequest

class AppRepository(private var apiInterface: APIInterface) {

    suspend fun getAll(getRequest: GetRequest) =apiInterface.getImageVideo(getRequest)

    suspend fun upload(uploadRequest: UploadRequest) =apiInterface.uploadImageVideo(uploadRequest)

    suspend fun delete(deleteRequest: DeleteRequest)= apiInterface.deleteImageVideo(deleteRequest)


}