package com.example.tabi.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tabi.databinding.FragmentSearchCityBinding
import com.example.tabi.ui.activities.CityDetailActivity
import com.example.tabi.ui.adapters.CityAdapter
import com.example.tabi.viewmodel.CityViewModel

class SearchCityFragment : Fragment() {

    private lateinit var binding: FragmentSearchCityBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var cityViewModel: CityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchCityBinding.inflate(inflater, container, false)

        // Setup RecyclerView
        cityAdapter = CityAdapter { city ->
            val intent = Intent(requireContext(), CityDetailActivity::class.java)
            intent.putExtra("cityId", city.id)
            intent.putExtra("cityName", city.name)
            intent.putExtra("thumbnailImage", city.thumbnailImg)
            startActivity(intent)
        }
        binding.recyclerViewCities.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCities.adapter = cityAdapter

        // Observasi data ViewModel
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        cityViewModel.fetchCityData()
        cityViewModel.cityData.observe(viewLifecycleOwner) { cities ->
            cityAdapter.submitList(cities)
        }

        return binding.root
    }
}
