package com.example.tabiku.repository

import android.util.Log
import com.example.tabiku.api.TranslationApiClient
import com.example.tabiku.model.TranslationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslationRepository {

    private val apiService = TranslationApiClient.apiService

    fun translateText(text: String, callback: (String?) -> Unit) {
        apiService.translateText(text).enqueue(object : Callback<TranslationResponse> {
            override fun onResponse(call: Call<TranslationResponse>, response: Response<TranslationResponse>) {
                if (response.isSuccessful) {
                    val translatedText = response.body()?.translated_text
                    callback(translatedText)
                } else {
                    Log.e("TranslationRepository", "API error: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                Log.e("TranslationRepository", "API error: ${t.message}")
                callback(null)
            }
        })
    }
}
