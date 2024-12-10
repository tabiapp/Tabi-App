package com.example.tabi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabi.databinding.ItemCitiesBinding
import com.example.tabi.model.CityData

class CityAdapter(private val onCityClick: (CityData) -> Unit) :
    ListAdapter<CityData, CityAdapter.CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city)
    }

    inner class CityViewHolder(private val binding: ItemCitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(city: CityData) {
            binding.cityName.text = city.name
            // Using Glide to load the image from URL or resource ID
            Glide.with(binding.root.context)
                .load(city.thumbnailImg) // Assuming 'thumbnailImg' is a URL
                .placeholder(com.example.tabi.R.drawable.city_image_placeholder) // Placeholder image
                .into(binding.cityImage)

            binding.root.setOnClickListener {
                onCityClick(city) // Handle item click
            }
        }
    }

    class CityDiffCallback : DiffUtil.ItemCallback<CityData>() {
        override fun areItemsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem == newItem
        }
    }
}
