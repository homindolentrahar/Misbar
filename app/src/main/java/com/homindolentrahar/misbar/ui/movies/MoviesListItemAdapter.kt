package com.homindolentrahar.misbar.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.MoviesListItemBinding
import com.homindolentrahar.misbar.domain.models.MoviesModel

class MoviesListItemAdapter(private val onClick: (item: MoviesModel) -> Unit) :
    PagingDataAdapter<MoviesModel, MoviesListItemAdapter.MoviesListItemHolder>(
        MoviesListItemComparator
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListItemHolder =
        MoviesListItemHolder.inflate(parent.context, parent)

    override fun onBindViewHolder(itemHolder: MoviesListItemHolder, position: Int) {
        val item = getItem(position)

        item?.let { model ->
            itemHolder.bind(model)

            itemHolder.itemView.setOnClickListener { onClick(model) }
        }
    }

    class MoviesListItemHolder(private val binding: MoviesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MoviesModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): MoviesListItemHolder {
                val binding =
                    MoviesListItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return MoviesListItemHolder(binding)
            }
        }

    }

    companion object MoviesListItemComparator : DiffUtil.ItemCallback<MoviesModel>() {
        override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean =
            oldItem == newItem
    }
}