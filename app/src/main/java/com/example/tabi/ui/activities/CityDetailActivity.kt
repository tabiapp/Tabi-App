package com.example.tabi.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.tabi.R
import com.example.tabi.adapters.CityPagerAdapter
import com.example.tabi.ui.fragments.MannersFragment
import com.example.tabi.ui.fragments.FoodsFragment
import com.example.tabi.ui.fragments.PlacesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CityDetailActivity : AppCompatActivity() {
    private lateinit var cityViewModel: CityViewModel
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        val cityName = intent.getStringExtra("cityName") ?: return
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        // Fetch city details from the ViewModel
        cityViewModel.fetchCityDetails(cityName)

        // Initialize fragments
        val fragments = listOf(
            MannersFragment(),
            FoodsFragment(),
            PlacesFragment()
        )

        // Set up ViewPager2 with the fragments
        viewPager = findViewById(R.id.viewPager)
        val adapter = CityPagerAdapter(this, fragments)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager2
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Manners"
                1 -> "Foods"
                2 -> "Places"
                else -> null
            }
        }.attach()

        // Observe city details and pass data to fragments
        cityViewModel.cityDetails.observe(this, Observer { cityData ->
            (fragments[0] as MannersFragment).setData(cityData.manners)
            (fragments[1] as FoodsFragment).setData(cityData.foods)
            (fragments[2] as PlacesFragment).setData(cityData.places)
        })
    }
}
