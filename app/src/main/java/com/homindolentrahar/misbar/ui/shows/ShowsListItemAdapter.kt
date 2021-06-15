package com.homindolentrahar.misbar.ui.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.homindolentrahar.misbar.databinding.ShowsListItemBinding
import com.homindolentrahar.misbar.domain.models.ShowsModel

class ShowsListItemAdapter(private val onClick: (item: ShowsModel) -> Unit) :
    PagingDataAdapter<ShowsModel, ShowsListItemAdapter.ShowsListItemHolder>(ShowsListItemComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsListItemHolder =
        ShowsListItemHolder.inflate(parent.context, parent)

    override fun onBindViewHolder(holder: ShowsListItemHolder, position: Int) {
        val item = getItem(position)

        item?.let { model ->
            holder.bind(model)

            holder.itemView.setOnClickListener { onClick(model) }
        }
    }

    class ShowsListItemHolder(private val binding: ShowsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShowsModel) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun inflate(context: Context, parent: ViewGroup): ShowsListItemHolder {
                val binding = ShowsListItemBinding.inflate(LayoutInflater.from(context), parent, false)
                return ShowsListItemHolder(binding)
            }
        }

    }

    companion object ShowsListItemComparator : DiffUtil.ItemCallback<ShowsModel>() {
        override fun areItemsTheSame(oldItem: ShowsModel, newItem: ShowsModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShowsModel, newItem: ShowsModel): Boolean =
            oldItem == newItem
    }
}