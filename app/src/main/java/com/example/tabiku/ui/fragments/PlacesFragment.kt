package com.example.tabiku.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabiku.R
import com.example.tabiku.repository.CityRepository
import com.example.tabiku.ui.adapters.PlacesAdapter
import com.example.tabiku.model.Place

class PlacesFragment : Fragment() {
    private lateinit var recyclerViewPlaces: RecyclerView
    private lateinit var placesAdapter: PlacesAdapter
    private lateinit var cityRepository: CityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_places, container, false)
        recyclerViewPlaces = view.findViewById(R.id.rvPlaces)
        recyclerViewPlaces.layoutManager = LinearLayoutManager(context)

        cityRepository = CityRepository()

        val regionName = arguments?.getString("region_name")
        regionName?.let { fetchPlaces(it) }

        return view
    }

    private fun fetchPlaces(regionName: String) {
        cityRepository.getPlaces(regionName) { placeResponse ->
            if (placeResponse?.message != null && placeResponse.message.isNotEmpty()) {
                placesAdapter = PlacesAdapter(placeResponse.message)
                recyclerViewPlaces.adapter = placesAdapter
                Log.d("API Response", "Places loaded: ${placeResponse.message.size} items")
            } else {
                Log.e("API Response", "Cannot load places.")
            }
        }
    }

}
