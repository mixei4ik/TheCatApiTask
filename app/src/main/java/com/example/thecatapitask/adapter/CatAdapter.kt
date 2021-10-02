package com.example.thecatapitask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.thecatapitask.R
import com.example.thecatapitask.data.Cat

class CatAdapter : PagingDataAdapter<Cat, CatViewHolder>(DiffUtilCallBack()) {

/*    private val items = mutableListOf<Cat>()*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return CatViewHolder(view)
    }

/*    override fun getItemCount(): Int {
        return items.size
    }*/

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
/*        val imageUrl = items[position].imageUrl ?: ""
        holder.bind(imageUrl)*/
        holder.bind(getItem(position)!!)
    }

/*    fun addItems(newItems: List<Cat>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }*/

    class  DiffUtilCallBack: DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.imageUrl == newItem.imageUrl
        }

    }
}

class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imageView = view.findViewById<ImageView>(R.id.imageView)

    fun bind(data: Cat) {
        imageView.load(data.imageUrl)
    }
}