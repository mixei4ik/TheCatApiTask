package com.example.thecatapitask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.thecatapitask.R
import com.example.thecatapitask.data.Cat

class CatAdapter : RecyclerView.Adapter<CatViewHolder>() {

    private val items = mutableListOf<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, null)
        return CatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val fileName = items[position].id ?: ""
        val imageUrl = items[position].imageUrl ?: ""
        holder.bind(fileName, imageUrl)
    }

    fun addItems(newItems: List<Cat>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imageView = view.findViewById<ImageView>(R.id.imageView)

    fun bind(catName: String, imageUrl: String) {
        imageView.load(imageUrl)
    }
}