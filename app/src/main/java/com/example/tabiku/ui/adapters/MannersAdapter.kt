package com.example.tabiku.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.tabiku.model.Manner
import com.example.tabiku.R

class MannersAdapter(private val manners: List<Manner>) : RecyclerView.Adapter<MannersAdapter.MannerViewHolder>() {

    class MannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transportationEtiquette: TextView = itemView.findViewById(R.id.transport)
        val socialInteractions: TextView = itemView.findViewById(R.id.social)
        val visitingHome: TextView = itemView.findViewById(R.id.visiting)
        val shoppingBargaining: TextView = itemView.findViewById(R.id.shopping)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manner, parent, false)
        return MannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: MannerViewHolder, position: Int) {
        val manner = manners[position]

        // Set the lists into the respective TextViews
        holder.transportationEtiquette.text = manner.transportation_etiquette.joinToString("\n")
        holder.socialInteractions.text = manner.social_interactions.joinToString("\n")
        holder.visitingHome.text = manner.visiting_home.joinToString("\n")
        holder.shoppingBargaining.text = manner.shopping_bargaining.joinToString("\n")
    }

    override fun getItemCount() = manners.size
}
