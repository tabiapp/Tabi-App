package com.example.tabiku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabiku.repository.TranslationRepository

class TranslationViewModel : ViewModel() {

    private val translationRepository = TranslationRepository()

    private val _translatedText = MutableLiveData<String>()
    val translatedText: LiveData<String> get() = _translatedText

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun translateText(text: String) {
        if (text.isBlank()) {
            _errorMessage.value = "Input text cannot be empty"
            return
        }

        translationRepository.translateText(text) { translatedText ->
            if (translatedText != null) {
                _translatedText.value = translatedText
                _errorMessage.value = null
                Log.d("TranslationViewModel", "Translation successful: $translatedText")
            } else {
                _errorMessage.value = "Failed to translate text. Please try again."
                Log.e("TranslationViewModel", "Translation failed")
            }
        }
    }
}
