package com.example.pkm_2.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://localhost:7861/"  // Use your computer's IP
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(createHeaderInterceptor())
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun createHeaderInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .addHeader("ngrok-skip-browser-warning", "69420")  // ✅ Try different value
            .addHeader("User-Agent", "PKM-Android-App")
        chain.proceed(requestBuilder.build())
    }

    private fun createLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // Changed to BODY to see response
    }

    // ✅ CREATE A LENIENT GSON INSTANCE
    private val gson by lazy {
        GsonBuilder()
            .setLenient()  // Accept malformed JSON
            .create()
    }

    val apiService: LangflowApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))  // ✅ Use lenient Gson
            .build()
            .create(LangflowApiService::class.java)
    }
}