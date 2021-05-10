package com.homindolentrahar.misbar.ui.core

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.ListItemBinding
import com.homindolentrahar.misbar.domain.core.ItemModel

class ListItemAdapter(private val onClick: (item: ItemModel) -> Unit) :
    ListAdapter<ItemModel, ListItemAdapter.MoviesPopularHolder>(MoviesPopularComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesPopularHolder =
        MoviesPopularHolder.inflate(parent.context, parent)

    override fun onBindViewHolder(holder: MoviesPopularHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener { onClick(item) }
    }

    class MoviesPopularHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): MoviesPopularHolder {
                val binding = ListItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return MoviesPopularHolder(binding)
            }
        }

    }

    companion object MoviesPopularComparator : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
            oldItem == newItem
    }
}