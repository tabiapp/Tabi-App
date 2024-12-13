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
import com.example.tabiku.ui.adapters.MannersAdapter

class MannersFragment : Fragment() {
    private lateinit var recyclerViewManners: RecyclerView
    private lateinit var mannersAdapter: MannersAdapter
    private lateinit var cityRepository: CityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manners, container, false)
        recyclerViewManners = view.findViewById(R.id.rvManners)
        recyclerViewManners.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with empty data to avoid RecyclerView not showing anything
        mannersAdapter = MannersAdapter(emptyList())
        recyclerViewManners.adapter = mannersAdapter

        cityRepository = CityRepository()

        val regionName = arguments?.getString("region_name")
        regionName?.let {
            fetchManners(it)
        } ?: Log.e("MannersFragment", "Region name is missing")

        return view
    }

    private fun fetchManners(regionName: String) {
        cityRepository.getManners(regionName) { manners ->
            manners?.message?.let {
                if (it.isNotEmpty()) {
                    // Update the adapter with the fetched data
                    mannersAdapter = MannersAdapter(it)
                    recyclerViewManners.adapter = mannersAdapter
                    Log.d("API Response", "Manners loaded: ${it.size} items")
                } else {
                    Log.e("API Response", "No manners data.")
                }
            } ?: Log.e("API Response", "Cannot load manners.")
        }
    }
}
