package com.example.tabi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.databinding.ItemCitiesBinding
import com.example.tabi.model.CityData

class CityAdapter(private val onCityClick: (CityData) -> Unit) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var cityList = listOf<CityData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.bind(city)
    }

    override fun getItemCount() = cityList.size

    fun submitList(list: List<CityData>) {
        cityList = list
        notifyDataSetChanged()
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
}
