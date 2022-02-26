package com.app.pepul

import android.app.Application

class IVApplication : Application() {

    val appRepository by lazy { AppRepository(APIConstant.getClient().create(APIInterface::class.java)) }
}