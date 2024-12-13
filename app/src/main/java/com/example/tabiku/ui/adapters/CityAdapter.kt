package com.example.tabiku.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import com.example.tabiku.R
import com.example.tabiku.model.City
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabiku.ui.activities.CityDetailActivity

class CityAdapter(private val cities: List<City>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgIconCity: ImageView = itemView.findViewById(R.id.cityIcon)
        val nameCity: TextView = itemView.findViewById(R.id.cityName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.nameCity.text = city.name

        if (city.iconCity.isNotEmpty()) {
            Glide.with(holder.itemView.context).load(city.iconCity).into(holder.imgIconCity)
        } else {
            Log.e("CityAdapter", "Icon URL not found for: ${city.name}")
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CityDetailActivity::class.java)
            intent.putExtra("region_name", city.name)
            intent.putExtra("thumbnail_url", city.thumbnailImg)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = cities.size
}
