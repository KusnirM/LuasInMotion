package com.example.luasinmotionandroid.presentation.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.luasinmotionandroid.R
import com.example.luasinmotionandroid.domain.model.Tram
import com.example.luasinmotionandroid.utils.inflate

class TramAdapter : RecyclerView.Adapter<TramViewHolder>() {

    private val items = mutableListOf<Tram>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TramViewHolder {
        return TramViewHolder(parent.inflate(R.layout.list_item_tram))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TramViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Tram>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}
