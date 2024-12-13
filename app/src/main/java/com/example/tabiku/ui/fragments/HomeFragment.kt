package com.example.tabiku.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabiku.R
import com.example.tabiku.repository.CityRepository
import com.example.tabiku.ui.adapters.CityAdapter
import com.example.tabiku.viewmodel.CityViewModel
import com.example.tabiku.viewmodel.TranslationViewModel
import java.util.*

class HomeFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var recyclerViewCities: RecyclerView
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityRepository: CityRepository
    private lateinit var cityViewModel: CityViewModel
    private lateinit var translationViewModel: TranslationViewModel

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var inputEnglish: EditText
    private lateinit var inputIndonesian: EditText
    private lateinit var micIcon: ImageView
    private lateinit var speakerIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize Views
        recyclerViewCities = view.findViewById(R.id.rvCities)
        recyclerViewCities.layoutManager = GridLayoutManager(context, 2)

        inputEnglish = view.findViewById(R.id.inputEnglish)
        inputIndonesian = view.findViewById(R.id.inputIndonesian)
        micIcon = view.findViewById(R.id.micIcon)
        speakerIcon = view.findViewById(R.id.speakerIcon)

        cityViewModel = ViewModelProvider(this)[CityViewModel::class.java]
        cityRepository = CityRepository()

        translationViewModel = ViewModelProvider(this)[TranslationViewModel::class.java]

        // Initialize Text-to-Speech
        textToSpeech = TextToSpeech(requireContext(), this)

        // Initialize Speech Recognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())

        fetchSomeCities()

        // Add TextWatcher for Auto Translate (Text-to-Text Translation)
        inputEnglish.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (it.isNotEmpty()) translateTextToIndonesian(it.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Mic Icon for Speech-to-Text
        micIcon.setOnClickListener { startSpeechToText() }

        // Speaker Icon for Text-to-Speech
        speakerIcon.setOnClickListener {
            val text = inputIndonesian.text.toString()
            if (text.isNotEmpty()) speakOut(text)
        }

        return view
    }

    private fun fetchSomeCities() {
        cityRepository.getAllCities { cities ->
            if (cities.isNotEmpty()) {
                cityAdapter = CityAdapter(cities.take(4))
                recyclerViewCities.adapter = cityAdapter
                recyclerViewCities.visibility = View.VISIBLE
            } else {
                recyclerViewCities.visibility = View.GONE
            }
        }
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("en", "US"))
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")

        speechRecognizer.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onResults(bundle: Bundle?) {
                val matches = bundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val spokenText = matches[0]
                    inputEnglish.setText(spokenText) // Set the recognized speech into inputEnglish
                }
            }

            override fun onError(error: Int) {
                Log.e("SpeechRecognizer", "Error: $error")
            }

            // Unused methods for simplicity
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer.startListening(intent)
    }

    private fun translateTextToIndonesian(text: String) {
        translationViewModel.translateText(text) // Hanya kirim teks
        translationViewModel.translatedText.observe(viewLifecycleOwner) { translatedText ->
            inputIndonesian.setText(translatedText) // Set translated text into inputIndonesian
        }
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("id", "ID")
        }
    }

    override fun onDestroy() {
        textToSpeech.shutdown()
        speechRecognizer.destroy()
        super.onDestroy()
    }
}
