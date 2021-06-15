package com.homindolentrahar.misbar.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.FavoritesListItemBinding
import com.homindolentrahar.misbar.domain.models.FavoritesModel

class FavoritesListItemAdapter(private val onClick: (item: FavoritesModel) -> Unit) :
    PagingDataAdapter<FavoritesModel, FavoritesListItemAdapter.FavoritesListItemHolder>(
        FavoritesListItemComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesListItemHolder =
        FavoritesListItemHolder.from(parent.context, parent)

    override fun onBindViewHolder(holder: FavoritesListItemHolder, position: Int) {
        val item = getItem(position)

        item?.let {
            holder.bind(item)
            holder.itemView.setOnClickListener { onClick(item) }
        }
    }

    class FavoritesListItemHolder(private val binding: FavoritesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoritesModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(context: Context, parent: ViewGroup): FavoritesListItemHolder {
                val binding =
                    FavoritesListItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return FavoritesListItemHolder(binding)
            }
        }
    }

    companion object FavoritesListItemComparator : DiffUtil.ItemCallback<FavoritesModel>() {
        override fun areItemsTheSame(oldItem: FavoritesModel, newItem: FavoritesModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FavoritesModel, newItem: FavoritesModel): Boolean =
            oldItem == newItem
    }
}