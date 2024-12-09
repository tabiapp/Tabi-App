package com.example.tabi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R

class MannersAdapter(private var manners: List<Pair<String, String>>) : RecyclerView.Adapter<MannersAdapter.MannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manners, parent, false)
        return MannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MannerViewHolder, position: Int) {
        holder.bind(manners[position])
    }

    override fun getItemCount(): Int = manners.size

    /**
     * Updates the adapter data and refreshes the view.
     *
     * @param newManners The new list of manners to be displayed.
     */
    fun updateData(newManners: List<Pair<String, String>>) {
        manners = newManners
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class for MannersAdapter to bind each item view.
     */
    class MannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)

        /**
         * Binds a manner data to the view.
         *
         * @param manner A pair of category title and description content.
         */
        fun bind(manner: Pair<String, String>) {
            titleText.text = manner.first // Category title
            descriptionText.text = manner.second // Etiquette content
        }
    }
}
