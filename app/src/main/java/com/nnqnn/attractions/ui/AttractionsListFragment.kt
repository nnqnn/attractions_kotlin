package com.nnqnn.attractions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.nnqnn.attractions.R
import com.nnqnn.attractions.model.AttractionCategory
import com.nnqnn.attractions.ui.adapter.AttractionAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AttractionsListFragment : Fragment() {

    private val viewModel: AttractionsViewModel by sharedViewModel()
    private lateinit var adapter: AttractionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attractions_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val search: TextInputEditText = view.findViewById(R.id.inputSearch)
        val chips: ChipGroup = view.findViewById(R.id.chipGroup)
        val favs: CheckBox = view.findViewById(R.id.checkFavorites)
        val recycler: RecyclerView = view.findViewById(R.id.recycler)

        adapter = AttractionAdapter(
            onClick = { showDetails(it) },
            onFav = { viewModel.toggleFavorite(it.id) },
            isFavorite = { id -> viewModel.favorites.value?.contains(id) == true }
        )
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        AttractionCategory.values().forEach { cat ->
            val chip = layoutInflater.inflate(R.layout.view_filter_chip, chips, false) as Chip
            chip.text = cat.label
            chip.setOnClickListener {
                val selected = if (chips.checkedChipId == chip.id) cat else null
                viewModel.setCategory(selected)
            }
            chips.addView(chip)
        }

        search.addTextChangedListener { viewModel.setQuery(it?.toString().orEmpty()) }
        favs.setOnCheckedChangeListener { _, _ -> viewModel.toggleFavoritesOnly() }

        viewModel.items.observe(viewLifecycleOwner) { adapter.submit(it) }
        viewModel.favorites.observe(viewLifecycleOwner) { adapter.notifyDataSetChanged() }
    }

    private fun showDetails(attraction: com.nnqnn.attractions.model.Attraction) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, AttractionDetailsDialog.newInstance(attraction.id))
            .addToBackStack(null)
            .commit()
    }
}

