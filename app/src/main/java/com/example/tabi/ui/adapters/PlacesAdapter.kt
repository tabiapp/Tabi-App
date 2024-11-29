package com.example.tabi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R
import com.example.tabi.model.Place
import com.squareup.picasso.Picasso

class PlacesAdapter(private val places: List<Place>) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_places, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val placeImage: ImageView = view.findViewById(R.id.imageViewPlace)
        private val placeName: TextView = view.findViewById(R.id.textViewPlaceName)

        fun bind(place: Place) {
            Picasso.get().load(place.img).into(placeImage)
            placeName.text = place.name

        }
    }
}
