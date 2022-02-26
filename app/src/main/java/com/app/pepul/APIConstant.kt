package com.app.pepul

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

object APIConstant {

    const val BASE_URL="https://nextgenerationsocialnetwork.com/user_details"
    private lateinit var retrofit: Retrofit

    fun getClient(): Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

       val clientBuilder=OkHttpClient.Builder()
           .callTimeout(60,TimeUnit.SECONDS);

        val gson = GsonBuilder()
            .setLenient()
            .create()

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(LogJson2Interceptor())
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }


    class LogJson2Interceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val response: Response = chain.proceed(request)
            val rawJson = response.body?.string()
            try {
                val `object` = JSONTokener(rawJson).nextValue()
                // String jsonLog = object instanceof JSONObject
// ? ((JSONObject) object).toString(4)
// : ((JSONArray) object).toString(4);
                if (`object` is JSONObject) {
                    val index = request.url.toString().lastIndexOf('/')
                    Log.d(
                        TAG, String.format(
                            "%s <-- %d Response: %s", request.url.toString().substring(index),
                            response.code,
                            `object`.toString(4)
                        )
                    )
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                val index = request.url.toString().lastIndexOf('/')
                Log.d(TAG, "${request.url.toString().substring(index)} <-- ${response.code} Raw response: $rawJson")
            }

// Re-create the response before returning it because body can be read only once
            return response.newBuilder().body(ResponseBody.create(response.body?.contentType(),
                rawJson!!
            )).build()
        }

        companion object {
            private val TAG = LogJson2Interceptor::class.java.simpleName
        }
    }
}