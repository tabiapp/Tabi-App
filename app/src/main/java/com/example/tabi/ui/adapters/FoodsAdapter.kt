package com.example.tabi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R
import com.example.tabi.model.Food
import com.squareup.picasso.Picasso

class FoodsAdapter(private val foods: List<Food>) : RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_foods, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    override fun getItemCount(): Int = foods.size

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val foodImage: ImageView = view.findViewById(R.id.imageViewFood)
        private val foodName: TextView = view.findViewById(R.id.textViewFoodName)


        fun bind(food: Food) {
            Picasso.get().load(food.img).into(foodImage)
            foodName.text = food.name
        }
    }
}
