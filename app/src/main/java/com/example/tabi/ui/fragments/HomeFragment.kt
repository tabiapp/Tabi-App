package com.example.tabi.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
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

    companion object {
        const val REQUEST_CODE_SPEECH_INPUT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up RecyclerView with GridLayoutManager for a grid-like layout
        cityAdapter = CityAdapter { city ->
            Toast.makeText(requireContext(), "Clicked on ${city.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewCities.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCities.adapter = cityAdapter

        // Set up ViewModel and fetch city data
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        cityViewModel.fetchCityData()
        Log.d("City Data", cityViewModel.toString())// Update adapter with city data


        cityViewModel.cityData.observe(viewLifecycleOwner, Observer { cities ->
            cities?.let {
                cityList = it.take(4) // Take max of 4 cities
                cityAdapter.submitList(cityList)
            } ?: run {
                Toast.makeText(requireContext(), "Data kota tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        })

        // Set up Speech-to-Text button
        binding.micIcon.setOnClickListener {
            promptSpeechInput()
        }

        // Set up Speaker Icon to translate and show the text
        binding.speakerIcon.setOnClickListener {
            val textToTranslate = binding.inputEnglish.text.toString()
            translateText(textToTranslate) // Translate text and update UI
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
            result?.let {
                val speechText = it[0]
                binding.inputEnglish.setText(speechText) // Display recognized speech in English input box
                translateText(speechText) // Translate text
            }
        }
    }

    // Translate text (using the provided text-to-text translation API)
    private fun translateText(text: String) {
        val apiUrl = "https://translate-api-65059410484.asia-southeast2.run.app/translate" // Updated API URL

        // Prepare data for the translation request
        val requestBody = JSONObject().apply {
            put("source_language", "en")  // Source language is English
            put("target_language", "id")  // Target language is Indonesian
            put("text", text)             // The text to be translated
        }

        // Request translation using Volley
        val request = JsonObjectRequest(
            Request.Method.POST, apiUrl, requestBody,
            { response ->
                val translatedText = response.getString("translated_text")
                binding.inputIndonesian.setText(translatedText) // Display translated text
            },
            { error ->
                Log.e("Translation Error", "Error: ${error.message}")
                Toast.makeText(
                    requireContext(),
                    "Translation failed: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        // Execute the request
        com.android.volley.toolbox.Volley.newRequestQueue(requireContext()).add(request)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
