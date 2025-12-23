package com.nnqnn.attractions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nnqnn.attractions.R
import com.nnqnn.attractions.data.todayHours
import com.nnqnn.attractions.model.Attraction

class AttractionAdapter(
    private val onClick: (Attraction) -> Unit,
    private val onFav: (Attraction) -> Unit,
    private val isFavorite: (Int) -> Boolean
) : RecyclerView.Adapter<AttractionAdapter.Holder>() {

    private val items = mutableListOf<Attraction>()

    fun submit(list: List<Attraction>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attraction, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.bind(item, isFavorite(item.id), onClick, onFav)
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val subtitle: TextView = itemView.findViewById(R.id.subtitle)
        private val desc: TextView = itemView.findViewById(R.id.desc)
        private val info: TextView = itemView.findViewById(R.id.info)
        private val fav: ImageButton = itemView.findViewById(R.id.btnFav)

        fun bind(
            item: Attraction,
            favorite: Boolean,
            onClick: (Attraction) -> Unit,
            onFav: (Attraction) -> Unit
        ) {
            title.text = item.name
            subtitle.text = "${item.category.label} • ${item.address}"
            desc.text = item.shortDescription
            info.text = "Сегодня: ${item.schedule.todayHours()} • ${item.price}"
            fav.setImageResource(if (favorite) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off)
            itemView.setOnClickListener { onClick(item) }
            fav.setOnClickListener { onFav(item) }
        }
    }
}

