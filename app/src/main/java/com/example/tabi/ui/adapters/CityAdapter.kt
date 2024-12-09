package com.example.tabi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.databinding.ItemCitiesBinding
import com.example.tabi.model.CityData

class CityAdapter(private val onCityClick: (CityData) -> Unit) : ListAdapter<CityData, CityAdapter.CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city)
    }

    inner class CityViewHolder(private val binding: ItemCitiesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: CityData) {
            binding.cityName.text = city.name
            binding.cityImage.setImageResource(city.iconResId)
            binding.root.setOnClickListener {
                onCityClick(city)
            }
        }
    }

    class CityDiffCallback : DiffUtil.ItemCallback<CityData>() {
        override fun areItemsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem.id == newItem.id // Asumsi `id` adalah identifier unik untuk setiap kota
        }

        override fun areContentsTheSame(oldItem: CityData, newItem: CityData): Boolean {
            return oldItem == newItem
        }
    }
}
