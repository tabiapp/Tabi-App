package com.example.tabi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: EditText
    private lateinit var outputText: TextView
    private lateinit var translateButton: Button
    private lateinit var cityRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        outputText = findViewById(R.id.outputText)
        translateButton = findViewById(R.id.translateButton)
        cityRecyclerView = findViewById(R.id.cityRecyclerView)

        // Dummy data for cities
        val cities = listOf("Jakarta", "Yogyakarta", "Surabaya", "Bali")
        cityRecyclerView.layoutManager = LinearLayoutManager(this)
        cityRecyclerView.adapter = CityAdapter(cities) { city ->
            val intent = Intent(this, CityActivity::class.java)
            intent.putExtra("CITY_NAME", city)
            startActivity(intent)
        }

        // Translate button functionality
        translateButton.setOnClickListener {
            val input = inputText.text.toString()
            outputText.text = "Translated: $input" // Replace with API/ML logic
        }
    }
}