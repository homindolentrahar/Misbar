package com.homindolentrahar.misbar.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.MoviesCarouselItemBinding
import com.homindolentrahar.misbar.domain.models.MoviesModel

class MoviesCarouselItemAdapter(private val onClick: (item: MoviesModel) -> Unit) :
    ListAdapter<MoviesModel, MoviesCarouselItemAdapter.MoviesCarouselHolder>(
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

    class MoviesCarouselHolder(private val binding: MoviesCarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): MoviesCarouselHolder {
                val binding =
                    MoviesCarouselItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return MoviesCarouselHolder(binding)
            }
        }
    }

    companion object MoviesCarouselComparator : DiffUtil.ItemCallback<MoviesModel>() {
        override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean =
            oldItem == newItem
    }
}