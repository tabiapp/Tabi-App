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
import com.example.tabiku.ui.adapters.FoodsAdapter
import com.example.tabiku.model.Food

class FoodsFragment : Fragment() {
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var foodsAdapter: FoodsAdapter
    private lateinit var cityRepository: CityRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foods, container, false)
        recyclerViewFoods = view.findViewById(R.id.rvFoods)
        recyclerViewFoods.layoutManager = LinearLayoutManager(context)

        cityRepository = CityRepository()

        val regionName = arguments?.getString("region_name")
        regionName?.let { fetchFoods(it) }

        return view
    }

    private fun fetchFoods(regionName: String) {
        cityRepository.getFoods(regionName) { foodResponse ->
            if (foodResponse?.message != null && foodResponse.message.isNotEmpty()) {
                foodsAdapter = FoodsAdapter(foodResponse.message)
                recyclerViewFoods.adapter = foodsAdapter
                Log.d("API Response", "Foods loaded: ${foodResponse.message.size} items")
            } else {
                Log.e("API Response", "Cannot load foods.")
            }
        }
    }

}
