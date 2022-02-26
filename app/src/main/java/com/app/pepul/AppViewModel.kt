package com.app.pepul

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.pepul.model.*
import kotlinx.coroutines.*

class AppViewModel(private val appRepository: AppRepository) : ViewModel() {

    val errorMessage=MutableLiveData<String>()

    val getList=MutableLiveData<GetResponse>()
    val uploadData=MutableLiveData<UploadResponse>()
    val deleteData=MutableLiveData<DeleteResponse>()

    val loading=MutableLiveData<Boolean>()

    val exceptionHandler= CoroutineExceptionHandler { _, throwable ->
        onError("Exception Handler : ${throwable.localizedMessage}")
    }

    fun getAll(getRequest: GetRequest){
        loading.value=true
            CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
                val response=appRepository.getAll(getRequest)
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        getList.value=response.body()
                        loading.value=false
                    }else{
                        onError("Error : ${response.message()}")
                    }
                }
            }
    }

    fun uploadData(uploadRequest: UploadRequest){
        loading.value=true
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response=appRepository.upload(uploadRequest)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    uploadData.value=response.body()
                    loading.value=false
                }else{
                    onError("Error : ${response.message()}")
                }
            }
        }

    }

    fun deleteData(deleteRequest: DeleteRequest){
        loading.value=true
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = appRepository.delete(deleteRequest)
            if(response.isSuccessful){
                deleteData.value=response.body()
                loading.value=false
            }else{
                onError("Error : ${response.message()}")
            }
        }
    }

    private fun onError(message: String){
        errorMessage.value=message
        loading.value=false
    }

    class AppModelFactory(private val appRepository: AppRepository) :
        ViewModelProvider.Factory {

        /**
         * Creates a new instance of the given `Class`.
         *
         * @param modelClass a `Class` whose instance is requested
         * @return a newly created ViewModel
         */
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppModelFactory::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppModelFactory(appRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }
    }


}