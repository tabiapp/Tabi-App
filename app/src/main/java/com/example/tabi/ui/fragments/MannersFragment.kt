package com.example.tabi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R

class MannersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val mannersList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manners, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewManners)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SimpleTextAdapter(mannersList)
        return view
    }

    fun setData(manners: List<String>) {
        mannersList.clear()
        mannersList.addAll(manners)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
