package com.example.tabi.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabi.R
import com.example.tabi.model.Manner
import com.example.tabi.ui.adapters.MannersAdapter

class MannersFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var mannersList: List<Pair<String, String>> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_manners, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewManners)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MannersAdapter(mannersList)
        return view
    }

    /**
     * Sets the data for MannersFragment and updates the RecyclerView.
     *
     * @param manners The list of manner data to be displayed.
     */
    fun setData(manners: List<Manner>) {
        val flattenedList = mutableListOf<Pair<String, String>>()

        // Flatten the manners list to get all manners for display
        manners.forEach { manner ->
            flattenedList.addAll(flattenManners(manner))
        }

        mannersList = flattenedList
        (recyclerView.adapter as MannersAdapter).updateData(mannersList)
    }

    /**
     * Flattens a Manner object into a list of pairs for easy RecyclerView binding.
     *
     * @param manner The manner object containing etiquette details.
     * @return A list of pairs containing category titles and descriptions.
     */
    private fun flattenManners(manner: Manner): List<Pair<String, String>> {
        val flattenedList = mutableListOf<Pair<String, String>>()

        manner.transportation_etiquette.forEach {
            flattenedList.add("Transportation Etiquette" to it)
        }
        manner.social_interactions.forEach {
            flattenedList.add("Social Interactions" to it)
        }
        manner.visiting_home.forEach {
            flattenedList.add("Visiting Home" to it)
        }
        manner.shopping_bargaining.forEach {
            flattenedList.add("Shopping & Bargaining" to it)
        }

        return flattenedList
    }
}
