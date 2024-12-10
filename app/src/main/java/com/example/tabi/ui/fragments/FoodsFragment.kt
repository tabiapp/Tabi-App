package com.example.tabi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.databinding.FragmentFoodsBinding
import com.example.tabi.ui.adapters.FoodsAdapter
import com.example.tabi.model.Food

class FoodsFragment : Fragment() {

    private var _binding: FragmentFoodsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private val foodsList: MutableList<Food> = mutableListOf()
    private lateinit var foodsAdapter: FoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Menggunakan view binding untuk memudahkan akses ke tampilan
        _binding = FragmentFoodsBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerViewFoods
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi adapter
        foodsAdapter = FoodsAdapter(foodsList)
        recyclerView.adapter = foodsAdapter

        return binding.root
    }

    /**
     * Sets the data for FoodsFragment and updates the RecyclerView.
     *
     * @param foods The list of food data to be displayed.
     */
    fun setData(foods: List<Food>) {
        foodsList.clear()
        foodsList.addAll(foods)
        foodsAdapter.notifyDataSetChanged()

        // Menampilkan atau menyembunyikan view empty data (jika ada)
        if (foodsList.isEmpty()) {
            binding.recyclerViewFoods.visibility = View.GONE
            binding.recyclerViewFoods.visibility = View.VISIBLE
        } else {
            binding.recyclerViewFoods.visibility = View.VISIBLE
            binding.recyclerViewFoods.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Menghindari memory leak dengan membebaskan referensi binding
        _binding = null
    }
}
