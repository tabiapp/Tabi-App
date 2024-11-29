package com.example.tabi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R
import com.example.tabi.ui.adapters.FoodsAdapter
import com.example.tabi.model.Food

class FoodsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val foodsList: MutableList<Food> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foods, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewFoods)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = FoodsAdapter(foodsList)
        return view
    }

    fun setData(foods: List<Food>) {
        foodsList.clear()
        foodsList.addAll(foods)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}
