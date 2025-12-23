package com.nnqnn.attractions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnqnn.attractions.R
import com.nnqnn.attractions.data.MockAttractions
import com.nnqnn.attractions.ui.adapter.AttractionAdapter
import com.nnqnn.attractions.ui.view.MapOverlayView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MapFragment : Fragment() {

    private val viewModel: AttractionsViewModel by sharedViewModel()
    private lateinit var adapter: AttractionAdapter
    private lateinit var mapView: MapOverlayView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.mapView)
        val list: RecyclerView = view.findViewById(R.id.mapList)

        adapter = AttractionAdapter(
            onClick = { showDetails(it) },
            onFav = { viewModel.toggleFavorite(it.id) },
            isFavorite = { id -> viewModel.favorites.value?.contains(id) == true }
        )
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submit(items)
            mapView.setData(items, MockAttractions.bounds)
        }
        viewModel.favorites.observe(viewLifecycleOwner) { adapter.notifyDataSetChanged() }
    }

    private fun showDetails(attraction: com.nnqnn.attractions.model.Attraction) {
        AttractionDetailsDialog.newInstance(attraction.id)
            .show(parentFragmentManager, "details")
    }
}

