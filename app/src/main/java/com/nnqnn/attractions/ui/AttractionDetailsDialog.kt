package com.nnqnn.attractions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nnqnn.attractions.R
import com.nnqnn.attractions.data.todayHours
import com.nnqnn.attractions.domain.AttractionsRepository
import org.koin.android.ext.android.inject

class AttractionDetailsDialog : BottomSheetDialogFragment() {

    private var attractionId: Int = 0
    private val repository: AttractionsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attractionId = requireArguments().getInt(ARG_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_attraction_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = repository.getAll().firstOrNull { it.id == attractionId } ?: return
        view.findViewById<TextView>(R.id.title).text = item.name
        view.findViewById<TextView>(R.id.subtitle).text = "${item.category.label} • ${item.address}"
        view.findViewById<TextView>(R.id.desc).text = item.description
        view.findViewById<TextView>(R.id.info).text = "Сегодня: ${item.schedule.todayHours()} • ${item.price}"
        view.findViewById<TextView>(R.id.contact).text = listOfNotNull(
            item.contact.phone,
            item.contact.website,
            item.contact.email
        ).joinToString(separator = "\n")
    }

    companion object {
        private const val ARG_ID = "arg_attraction_id"
        fun newInstance(id: Int) = AttractionDetailsDialog().apply {
            arguments = Bundle().apply { putInt(ARG_ID, id) }
        }
    }
}

