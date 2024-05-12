package com.example.wavefinder.apiservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.airtable.com/v0/"
    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer patpaCLmFoJNytNOf.1a21475d1d5a30fb0e6a64d45671a770620b9fe17d421d77aca9d62bcb4d2379")
                .build()
            chain.proceed(request)
        })
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}