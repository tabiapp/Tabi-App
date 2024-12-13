package com.example.tabiku.api

import com.example.tabiku.model.TranslationRequest
import com.example.tabiku.model.TranslationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslationApiService {

    @GET("translate")
    fun translateText(
        @Query("text") text: String
    ): Call<TranslationResponse>
}
