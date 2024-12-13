package com.example.tabiku.ui.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tabiku.R
import com.example.tabiku.ui.adapters.SectionsPagerAdapter
import com.example.tabiku.ui.fragments.FoodsFragment
import com.example.tabiku.ui.fragments.MannersFragment
import com.example.tabiku.ui.fragments.PlacesFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CityDetailActivity : AppCompatActivity() {

    private lateinit var regionName: String
    private lateinit var thumbnailUrl: String
    private lateinit var tvCityName: TextView
    private lateinit var btnBack: ImageView
    private lateinit var ivThumbnail: ImageView
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        // Get data from Intent
        regionName = intent.getStringExtra("region_name") ?: "Unknown Region"
        thumbnailUrl = intent.getStringExtra("thumbnail_url") ?: ""

        // Bind views using findViewById
        tvCityName = findViewById(R.id.tvCityName)
        btnBack = findViewById(R.id.btnBack)
        ivThumbnail = findViewById(R.id.ivThumbnail)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // Set up UI
        tvCityName.text = regionName
        loadThumbnail(thumbnailUrl)

        // Back button functionality
        btnBack.setOnClickListener {
            finish()
        }

        // Set up ViewPager2 and TabLayout
        setupViewPager()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            MannersFragment().apply { arguments = Bundle().apply { putString("region_name", regionName) } },
            FoodsFragment().apply { arguments = Bundle().apply { putString("region_name", regionName) } },
            PlacesFragment().apply { arguments = Bundle().apply { putString("region_name", regionName) } }
        )

        val titles = listOf("Manners", "Foods", "Places")

        // Set the adapter for ViewPager2
        viewPager.adapter = SectionsPagerAdapter(this, fragments)

        // Attach TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun loadThumbnail(url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_city_placeholder) // Optional placeholder
                .into(ivThumbnail)
        } else {
            ivThumbnail.setImageResource(R.drawable.ic_image_not_found) // Fallback if no URL
        }
    }
}