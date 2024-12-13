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
import com.example.tabiku.model.Food
import com.example.tabiku.ui.adapters.FoodsAdapter

class FoodsFragment : Fragment() {
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var foodsAdapter: FoodsAdapter
    private lateinit var cityRepository: CityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foods, container, false)

        // Initialize RecyclerView and adapter
        recyclerViewFoods = view.findViewById(R.id.rvFoods)
        recyclerViewFoods.layoutManager = LinearLayoutManager(context)

        foodsAdapter = FoodsAdapter(ArrayList()) // Initialize with empty list
        recyclerViewFoods.adapter = foodsAdapter

        // Set the item click listener for the adapter
        foodsAdapter.setOnItemClickCallback(object : FoodsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Food) {
                // Handle the food item click event (you can navigate to another screen or show a toast)
                Log.d("FoodItem", "Clicked: ${data.name}")
            }
        })

        cityRepository = CityRepository()

        // Get region name from arguments and fetch foods
        val regionName = arguments?.getString("region_name")
        regionName?.let { fetchFoods(it) }

        return view
    }

    private fun fetchFoods(regionName: String) {
        cityRepository.getFoods(regionName) { foodResponse ->
            if (foodResponse?.message != null && foodResponse.message.isNotEmpty()) {
                // Update the data in the adapter
                foodsAdapter.updateData(foodResponse.message)
                Log.d("API Response", "Foods loaded: ${foodResponse.message.size} items")
            } else {
                Log.e("API Response", "Cannot load foods.")
            }
        }
    }
}
