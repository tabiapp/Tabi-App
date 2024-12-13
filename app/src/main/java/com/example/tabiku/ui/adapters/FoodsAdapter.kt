package com.example.tabiku.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabiku.R
import com.example.tabiku.model.Food

class FoodsAdapter(private val foods: ArrayList<Food>) : RecyclerView.Adapter<FoodsAdapter.FoodViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    // Method to set the item click callback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.foodName.text = food.name
        Glide.with(holder.itemView.context)
            .load(food.img) // Assuming `food.img` is a URL
            .placeholder(R.drawable.ic_food_placeholder) // Placeholder while image is loading
            .error(R.drawable.ic_image_not_found) // Error image if loading fails
            .into(holder.imgFood)

        // Set the click listener for the item
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(food)
        }
    }

    override fun getItemCount(): Int = foods.size

    // Method to update the list of foods
    fun updateData(newFoods: List<Food>) {
        foods.clear()
        foods.addAll(newFoods)
        notifyDataSetChanged()
    }

    // ViewHolder class to hold the views for each item
    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgFood: ImageView = itemView.findViewById(R.id.foodImage)
        val foodName: TextView = itemView.findViewById(R.id.foodName)
    }

    // Interface for item click callback
    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }
}
