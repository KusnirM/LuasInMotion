package com.example.luasinmotionandroid.presentation.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.luasinmotionandroid.domain.model.Tram
import kotlinx.android.synthetic.main.list_item_tram.view.*

class TramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: Tram) {
        itemView.dueMins.text = model.dueMins
        itemView.destination.text = model.destination
    }
}
