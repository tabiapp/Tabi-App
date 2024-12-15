package com.example.tabiku.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabiku.R
import com.example.tabiku.model.Food

class FoodsAdapter(private val foods: List<Food>) : RecyclerView.Adapter<FoodsAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFood: ImageView = itemView.findViewById(R.id.foodImage)
        val nameFood: TextView = itemView.findViewById(R.id.foodName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = foods[position]
        holder.nameFood.text = place.name
        Glide.with(holder.itemView.context).load(place.img).into(holder.imgFood)

    }

    override fun getItemCount() = foods.size
}
