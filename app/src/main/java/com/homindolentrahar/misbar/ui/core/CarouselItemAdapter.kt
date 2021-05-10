package com.homindolentrahar.misbar.ui.core

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.CarouselItemBinding
import com.homindolentrahar.misbar.domain.core.ItemModel

class CarouselItemAdapter(private val onClick: (item: ItemModel) -> Unit) :
    ListAdapter<ItemModel, CarouselItemAdapter.MoviesCarouselHolder>(
        MoviesCarouselComparator
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesCarouselHolder = MoviesCarouselHolder.inflate(parent.context, parent)

    override fun onBindViewHolder(
        holder: MoviesCarouselHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
//        Click
        holder.itemView.setOnClickListener { onClick(item) }
    }

    class MoviesCarouselHolder(private val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): MoviesCarouselHolder {
                val binding =
                    CarouselItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return MoviesCarouselHolder(binding)
            }
        }
    }

    companion object MoviesCarouselComparator : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
            oldItem == newItem
    }
}