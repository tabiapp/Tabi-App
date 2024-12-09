package com.example.tabi.ui.activities

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tabi.R
import com.example.tabi.ui.adapters.CityDetailAdapter
import com.example.tabi.ui.fragments.MannersFragment
import com.example.tabi.ui.fragments.FoodsFragment
import com.example.tabi.ui.fragments.PlacesFragment
import com.example.tabi.viewmodel.CityViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CityDetailActivity : AppCompatActivity() {
    private lateinit var cityViewModel: CityViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var cityTitle: TextView
    private lateinit var cityImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)

        // Ambil nama kota dari Intent
        val cityName = intent.getStringExtra("CITY_NAME") ?: return
        val thumbnailImg = intent.getStringExtra("THUMBNAIL_IMG") ?: ""

        // Inisialisasi ViewModel
        cityViewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        // Inisialisasi Views
        cityTitle = findViewById(R.id.cityTitle)
        cityImage = findViewById(R.id.cityImage)

        // Set nama kota dan gambar
        cityTitle.text = cityName
        Glide.with(this)
            .load(thumbnailImg)
            .centerCrop()
            .placeholder(R.drawable.city_image_placeholder)
            .into(cityImage)

        // Inisialisasi ViewPager2 dan adapter
        val fragments = listOf(
            MannersFragment(),
            FoodsFragment(),
            PlacesFragment()
        )
        viewPager = findViewById(R.id.viewPager)
        val adapter = CityDetailAdapter(this, fragments)
        viewPager.adapter = adapter

        // Inisialisasi TabLayout dan hubungkan dengan ViewPager2
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_manners)
                1 -> getString(R.string.tab_foods)
                2 -> getString(R.string.tab_places)
                else -> null
            }
        }.attach()

        // Panggil fetchCityData untuk mendapatkan data kota
        cityViewModel.fetchCityData(cityName)
        cityViewModel.fetchRegionDetails(cityName)

        // Observasi data kota dan pasangkan ke fragmen
        cityViewModel.cityData.observe(this, Observer { cityDataList ->
            // Jika cityDataList bukan null, kita ambil data pertama
            val cityData = cityDataList?.firstOrNull()
            cityData?.let {
                (fragments[0] as MannersFragment).setData(it.manners)
                (fragments[1] as FoodsFragment).setData(it.foods)
                (fragments[2] as PlacesFragment).setData(it.places)
            }
        })

        // Set listener untuk tombol back
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}
