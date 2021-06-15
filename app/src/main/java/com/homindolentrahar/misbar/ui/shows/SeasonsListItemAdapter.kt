package com.homindolentrahar.misbar.ui.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.SeasonsListItemBinding
import com.homindolentrahar.misbar.domain.models.ShowsSeasonsModel

class SeasonsListItemAdapter :
    ListAdapter<ShowsSeasonsModel, SeasonsListItemAdapter.SeasonsListItemHolder>(
        SeasonsListItemComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsListItemHolder {
        return SeasonsListItemHolder.inflate(parent.context, parent)
    }

    override fun onBindViewHolder(holder: SeasonsListItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class SeasonsListItemHolder(private val binding: SeasonsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShowsSeasonsModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): SeasonsListItemHolder {
                return SeasonsListItemHolder(
                    SeasonsListItemBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    companion object SeasonsListItemComparator : DiffUtil.ItemCallback<ShowsSeasonsModel>() {
        override fun areItemsTheSame(
            oldItem: ShowsSeasonsModel,
            newItem: ShowsSeasonsModel
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShowsSeasonsModel,
            newItem: ShowsSeasonsModel
        ): Boolean = oldItem == newItem
    }
}