package com.example.pkm_2.api

<<<<<<< HEAD
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
=======
import com.example.pkm_2.BuildConfig
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

<<<<<<< HEAD
    private const val BASE_URL = "http://localhost/"
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
=======
    private val BASE_URL = BuildConfig.LANGFLOW_BASE_URL
    private val API_KEY = BuildConfig.LANGFLOW_API_KEY

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                // Only add Authorization if not already present
                .apply {
                    if (original.header("Authorization") == null) {
                        addHeader("Authorization", "Bearer ${BuildConfig.LANGFLOW_API_KEY}")
                    }
                    if (original.header("Content-Type") == null) {
                        addHeader("Content-Type", "application/json")
                    }
                }
                .build()

            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
>>>>>>> d01ba51 (Initial commit: Android PKM app with Langflow integration)
