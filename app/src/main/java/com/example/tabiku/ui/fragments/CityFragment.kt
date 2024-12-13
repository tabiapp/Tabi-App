package com.example.tabiku.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabiku.R
import com.example.tabiku.repository.CityRepository
import com.example.tabiku.ui.adapters.CityAdapter

class CityFragment : Fragment() {
    private lateinit var recyclerViewCities: RecyclerView
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityRepository: CityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        recyclerViewCities = view.findViewById(R.id.rvCities)
        recyclerViewCities.layoutManager = GridLayoutManager(context, 2)

        cityAdapter = CityAdapter(emptyList())
        recyclerViewCities.adapter = cityAdapter

        cityRepository = CityRepository()

        fetchAllCities()

        return view
    }

    private fun fetchAllCities() {
        cityRepository.getAllCities { cities ->
            if (cities.isNotEmpty()) {
                cityAdapter = CityAdapter(cities)
                recyclerViewCities.adapter = cityAdapter
                Log.d("API Response", "Cities loaded: ${cities.size} items")
            } else {
                Log.e("API Response", "Cannot load cities.")
            }
        }
    }
}
