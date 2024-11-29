package com.example.tabi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R
import com.example.tabi.ui.adapters.PlacesAdapter
import com.example.tabi.model.Place

class PlacesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val placesList: MutableList<Place> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_places, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewPlaces)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PlacesAdapter(placesList) // Use the updated adapter
        return view
    }

    fun setData(places: List<Place>) {
        placesList.clear()
        placesList.addAll(places)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
