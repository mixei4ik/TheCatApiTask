package com.example.thecatapitask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.thecatapitask.Navigator
import com.example.thecatapitask.R
import com.example.thecatapitask.data.Cat

class CatAdapter(private val nav: Navigator) : PagingDataAdapter<Cat, CatViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position)!!)

        holder.itemView.setOnClickListener{
            nav.openCatInfoFragment(getItem(position)?.imageUrl!!, getItem(position)?.id!!)
        }


    }

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

