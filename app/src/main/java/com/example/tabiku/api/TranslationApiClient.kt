package com.example.tabiku.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TranslationApiClient {

    private const val BASE_URL = "https://translate-api-65059410484.asia-southeast2.run.app/" // Replace with your translation API URL

    val apiService: TranslationApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TranslationApiService::class.java)
    }
}
