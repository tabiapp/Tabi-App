package com.example.tabi.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tabi.R
import com.example.tabi.databinding.FragmentHomeBinding
import com.example.tabi.ui.activities.SearchCityActivity
import com.example.tabi.ui.adapters.CityAdapter
import com.example.tabi.viewmodel.CityViewModel
import com.example.tabi.model.CityData
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

        // Set up RecyclerView
        cityAdapter = CityAdapter { city ->
            // Handle city click (open city details or show a Toast)
            Toast.makeText(requireContext(), "Clicked on ${city.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewCities.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCities.adapter = cityAdapter

        // Set up ViewModel and fetch city data
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        cityViewModel.fetchCityData("Jakarta,Bali,Yogyakarta,Surabaya")
        cityViewModel.cityData.observe(viewLifecycleOwner, Observer { cities ->
            cityList = cities
            cityAdapter.submitList(cityList)
        })

        // Set up search button
        binding.searchButton.setOnClickListener {
            val intent = Intent(activity, SearchCityActivity::class.java)
            startActivity(intent)
        }

        // Set up speech-to-text button
        binding.speechButton.setOnClickListener {
            promptSpeechInput()
        }

        return binding.root
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == android.app.Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (result != null && result.isNotEmpty()) {
                val speechText = result[0]
                binding.translationText.text = "You said: $speechText"
            }
        }
    }
}
