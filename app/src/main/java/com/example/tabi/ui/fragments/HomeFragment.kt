package com.example.tabi.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tabi.R
import com.example.tabi.databinding.FragmentHomeBinding
import com.example.tabi.ui.adapters.CityAdapter
import com.example.tabi.viewmodel.CityViewModel
import com.example.tabi.model.CityData
import org.json.JSONObject
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityList: List<CityData>
    private lateinit var cityViewModel: CityViewModel
    private lateinit var textToSpeech: TextToSpeech

    companion object {
        const val REQUEST_CODE_SPEECH_INPUT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up RecyclerView with GridLayoutManager for a grid-like layout
        cityAdapter = CityAdapter { city ->
            // Handle city click (open city details or show a Toast)
            Toast.makeText(requireContext(), "Clicked on ${city.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewCities.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns
        binding.recyclerViewCities.adapter = cityAdapter

        // Set up ViewModel and fetch city data
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        cityViewModel.fetchCityData("Jakarta,Bali,Yogyakarta,Surabaya")
        cityViewModel.cityData.observe(viewLifecycleOwner, Observer { cities ->
            cityList = cities.take(4)
            cityAdapter.submitList(cityList) // Update adapter with city data
        })

        // Set up Text-to-Speech
        textToSpeech = TextToSpeech(requireContext(), OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale("id", "ID") // Set language to Indonesian for TTS
            } else {
                Log.e("TextToSpeech", "Initialization failed")
            }
        })

        // Set up Speech-to-Text button
        binding.micIcon.setOnClickListener {
            promptSpeechInput()
        }

        // Set up Speaker Icon to speak out translated text
        binding.speakerIcon.setOnClickListener {
            val textToTranslate = binding.inputEnglish.text.toString()
            translateText(textToTranslate)
        }

        return binding.root
    }

    // Speech-to-Text function to capture user speech and display the result
    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }

    // Handle the result of Speech-to-Text
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == android.app.Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (result != null && result.isNotEmpty()) {
                val speechText = result[0]
                binding.inputEnglish.setText(speechText) // Display the recognized speech in English input box
                translateText(speechText) // Call the translate function
            }
        }
    }

    // Translate text (In this example, using Google Translate API)
    private fun translateText(text: String) {
        val apiUrl = "https://translation.googleapis.com/language/translate/v2"
        val apiKey = "YOUR_GOOGLE_API_KEY" // Replace with your actual API key

        // Prepare data for the translation request
        val requestBody = JSONObject()
        requestBody.put("q", text)
        requestBody.put("target", "id") // Translate to Indonesian
        requestBody.put("source", "en") // From English

        // Request translation using Volley
        val request = JsonObjectRequest(
            com.android.volley.Request.Method.POST, apiUrl, requestBody,
            com.android.volley.Response.Listener { response ->
                val translatedText = response.getJSONObject("data")
                    .getJSONArray("translations")
                    .getJSONObject(0)
                    .getString("translatedText")
                binding.inputIndonesian.setText(translatedText) // Display translated text in Indonesian input box
                speakOut(translatedText) // Speak the translated text
            },
            com.android.volley.Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Translation failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        // Execute the request
        com.android.volley.toolbox.Volley.newRequestQueue(requireContext()).add(request)
    }

    // Function to speak out the translated text using Text-to-Speech
    private fun speakOut(text: String) {
        if (text.isNotEmpty()) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Toast.makeText(requireContext(), "No text to speak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
